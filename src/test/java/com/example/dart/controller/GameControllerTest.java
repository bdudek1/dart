package com.example.dart.controller;

import com.example.dart.model.Shot;
import com.example.dart.model.dto.GameDto;
import com.example.dart.model.dto.PlayerDto;
import com.example.dart.model.dto.ShotDto;
import com.example.dart.model.enums.GameState;
import com.example.dart.model.enums.ShotType;
import com.example.dart.model.exception.EntityNotFoundException;
import com.example.dart.model.exception.NotEnoughPlayersException;
import com.example.dart.service.GameService;
import com.example.dart.service.PlayerService;

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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.TestDataHolder.*;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private GameService gameServiceMock;
    @MockBean
    private PlayerService playerServiceMock;

    private GameController gameController;

    private static final String CREATE_GAME_URL = "/game";
    private static final String PERFORM_SHOT_URL = "/game/{id}/shot";
    private static final String GET_GAME_URL = "/game/{id}";
    private static final String GET_GAMES_URL = "/games";
    private static final String INVALID_JSON_BODY = "invalid json";

    @Test
    public void createGameTest() throws Exception {
        when(gameServiceMock.createGame(any())).thenReturn(TEST_GAME);

        this.mockMvc.perform(
                MockMvcRequestBuilders.post(CREATE_GAME_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(
                                new PlayerDto().playerCollectionToPlayerDtoCollection(TEST_PLAYERS)
                        ))
                )
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameState").value(GameState.IN_PROGRESS.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.finished").value(false));

        verify(gameServiceMock, times(1)).createGame(any());
    }

    @Test
    public void createGameTestWithNotEnoughPlayers() throws Exception {
        when(gameServiceMock.createGame(any())).thenThrow(new NotEnoughPlayersException());

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post(CREATE_GAME_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(
                                        new PlayerDto().playerCollectionToPlayerDtoCollection(List.of(TEST_PLAYER_1)))
                                )
                )
                .andExpect(status().isBadRequest());

        verify(gameServiceMock, times(1)).createGame(any());
    }

    @Test
    public void createGameTestWithInvalidJsonBody() throws Exception {
        when(gameServiceMock.createGame(any())).thenReturn(TEST_GAME);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post(CREATE_GAME_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(INVALID_JSON_BODY)
                )
                .andExpect(status().isBadRequest());

        verify(gameServiceMock, times(0)).createGame(any());
    }

    @Test
    public void getGameByIdTest() throws Exception {
        when(gameServiceMock.findGameById(any())).thenReturn(TEST_GAME);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get(GET_GAME_URL, 1L)
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameState").value(GameState.IN_PROGRESS.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.finished").value(false));

        verify(gameServiceMock, times(1)).findGameById(any());
    }

    @Test
    public void getGameByIdWithNonExistingGameTest() throws Exception {
        when(gameServiceMock.findGameById(any())).thenThrow(new EntityNotFoundException());

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get(GET_GAME_URL, 1L)
                )
                .andExpect(status().isNotFound());

        verify(gameServiceMock, times(1)).findGameById(any());
    }

    @Test
    public void getGamesByPlayerIdTest() throws Exception {
        when(gameServiceMock.findGamesByPlayerId(any())).thenReturn(List.of(TEST_GAME));

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get(GET_GAMES_URL)
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("playerId", "1")
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].gameState").value(GameState.IN_PROGRESS.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].finished").value(false));

        verify(gameServiceMock, times(1)).findGamesByPlayerId(any());
    }

    @Test
    public void performShotTest() throws Exception {
        when(gameServiceMock.addShotAndReturnGameDto(anyLong(), any(Shot.class))).thenReturn(new GameDto(TEST_GAME));

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post(PERFORM_SHOT_URL, 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(
                                        new ShotDto(20, ShotType.TRIPLE)
                                ))
                )
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.gameState").value(GameState.IN_PROGRESS.name()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.finished").value(false));

        verify(gameServiceMock, times(1)).addShotAndReturnGameDto(any(), any());
    }

    @Test
    public void performShotTestWithInvalidJsonBody() throws Exception {
        when(gameServiceMock.addShotAndReturnGameDto(anyLong(), any(Shot.class))).thenReturn(new GameDto(TEST_GAME));

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post(PERFORM_SHOT_URL, 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(INVALID_JSON_BODY)
                )
                .andExpect(status().isBadRequest());

        verify(gameServiceMock, times(0)).addShotAndReturnGameDto(any(), any());
    }

    @Test
    public void performTestWithInvalidShot() throws Exception {
        when(gameServiceMock.addShotAndReturnGameDto(anyLong(), any(Shot.class))).thenReturn(new GameDto(TEST_GAME));

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post(PERFORM_SHOT_URL, 1L)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(
                                        new ShotDto(30, ShotType.TRIPLE)
                                ))
                )
                .andExpect(status().isBadRequest());

        verify(gameServiceMock, times(0)).addShotAndReturnGameDto(any(), any());
    }

    @BeforeEach
    public void setup() {
        this.gameServiceMock = mock(GameService.class);
        this.playerServiceMock = mock(PlayerService.class);
        this.gameController = new GameController(gameServiceMock, playerServiceMock);
        this.mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }
}
