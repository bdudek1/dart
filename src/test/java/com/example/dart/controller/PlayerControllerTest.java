package com.example.dart.controller;

import com.example.dart.model.Player;
import com.example.dart.model.exception.EntityAlreadyExistsException;
import com.example.dart.model.exception.EntityNotFoundException;
import com.example.dart.service.PlayerService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.TestDataHolder.*;

@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private PlayerService playerServiceMock;

    private PlayerController playerController;
    private static final String PLAYERS_URL = "/players";
    private static final String PLAYER_URL = "/player";
    private static final String VALIDATE_PLAYER_CREDENTIALS_URL = "/player/validate-credentials";
    private static final String PLAYER_URL_WITH_ID = "/player/{id}";
    private static final String INCORRECT_JSON_NAME_PROPERTY = "namee";
    private static final int TEST_PLAYER_ID = 1;

    @Test
    public void testGetPlayers() throws Exception {
        when(playerServiceMock.findAllPlayers()).thenReturn(TEST_PLAYERS);

        this.mockMvc.perform(get(PLAYERS_URL).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value(TEST_PLAYER_1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value(TEST_PLAYER_2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].name").value(TEST_PLAYER_3.getName()));

        verify(playerServiceMock, times(1)).findAllPlayers();
    }

    @Test
    public void testGetPlayerById() throws Exception {
        when(playerServiceMock.findPlayerById(anyInt())).thenReturn(TEST_PLAYER_1);

        this.mockMvc.perform(get(PLAYER_URL_WITH_ID, TEST_PLAYER_ID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(TEST_PLAYER_1.getName()));

        verify(playerServiceMock, times(1)).findPlayerById(1);
    }

    @Test
    public void testGetPlayerByIdWithInvalidIdShouldReturnNotFound() throws Exception {
        when(playerServiceMock.findPlayerById(anyInt())).thenThrow(new EntityNotFoundException());

        this.mockMvc.perform(get(PLAYER_URL_WITH_ID, TEST_PLAYER_ID).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(playerServiceMock, times(1)).findPlayerById(1);
    }

    @Test
    public void testGetPlayerByName() throws Exception {
        when(playerServiceMock.findPlayerByName(anyString())).thenReturn(TEST_PLAYER_1);

        this.mockMvc.perform(get(PLAYER_URL)
                        .param("name", TEST_PLAYER_1.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(TEST_PLAYER_1.getName()));

        verify(playerServiceMock, times(1)).findPlayerByName(TEST_PLAYER_1.getName());
    }

    @Test
    public void testGetPlayerByNameWithInvalidNameShouldReturnNotFound() throws Exception {
        when(playerServiceMock.findPlayerByName(anyString())).thenThrow(new EntityNotFoundException());

        this.mockMvc.perform(get(PLAYER_URL)
                        .param("name", TEST_PLAYER_1.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

        verify(playerServiceMock, times(1)).findPlayerByName(TEST_PLAYER_1.getName());
    }

    @Test
    public void testCreatePlayer() throws Exception {
        when(playerServiceMock.createPlayer(any(Player.class))).thenReturn(TEST_PLAYER_1);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post(PLAYER_URL)
                                      .contentType(MediaType.APPLICATION_JSON)
                                      .content(new ObjectMapper().writeValueAsString(TEST_PLAYER_1))
                )
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(TEST_PLAYER_1.getName()));

        verify(playerServiceMock, times(1)).createPlayer(any(Player.class));
    }

    @Test
    public void testCreatePlayerWhenPlayerExistsShouldReturnConflict() throws Exception {
        when(playerServiceMock.createPlayer(any(Player.class))).thenThrow(new EntityAlreadyExistsException());

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post(PLAYER_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(TEST_PLAYER_1))
                )
                .andExpect(status().isConflict());

        verify(playerServiceMock, times(1)).createPlayer(any(Player.class));
    }

    @Test
    public void testCreatePlayerShouldReturnBadRequestInCaseOfInvalidJsonBody() throws Exception {
        when(playerServiceMock.createPlayer(any(Player.class))).thenReturn(TEST_PLAYER_1);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post(PLAYER_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(TEST_PLAYER_1).replace("name", INCORRECT_JSON_NAME_PROPERTY))
                )
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testArePlayerCredentialsValid() throws Exception {
        when(playerServiceMock.arePlayerCredentialsValid(TEST_PLAYER_NAME_1, String.valueOf(TEST_PASSWORD))).thenReturn(true);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get(VALIDATE_PLAYER_CREDENTIALS_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("name", TEST_PLAYER_1.getName())
                                .param("password", String.valueOf(TEST_PASSWORD))
                )
                .andExpect(status().isOk());
    }

    @BeforeEach
    public void setup() {
        this.playerServiceMock = mock(PlayerService.class);
        this.playerController = new PlayerController(playerServiceMock);
        this.mockMvc = MockMvcBuilders.standaloneSetup(playerController).build();
    }
}
