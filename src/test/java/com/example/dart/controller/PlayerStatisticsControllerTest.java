package com.example.dart.controller;

import com.example.dart.model.dto.PlayerStatisticsDto;
import com.example.dart.model.enums.PlayerStatisticsOrderType;
import com.example.dart.service.PlayerStatisticsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static utils.TestDataHolder.*;

@WebMvcTest(PlayerStatisticsController.class)
public class PlayerStatisticsControllerTest {

    private MockMvc mockMvc;

    @MockBean
    private PlayerStatisticsService playerStatisticsServiceMock;

    private PlayerStatisticsController playerStatisticsController;

    private static final String GET_PLAYER_STATISTICS_URL = "/player-statistics";
    private static final String INVALID_JSON_VALUE = "invalid_json";

    @Test
    public void testGetPlayerStatistics() throws Exception {
        when(playerStatisticsServiceMock.getOrderedPlayerStatistics(any(PlayerStatisticsOrderType.class), any(Integer.class)))
                                        .thenReturn(new PlayerStatisticsDto().playerStatisticsListToPlayerStatisticsListDto(TEST_PLAYER_STATISTICS));

        this.mockMvc.perform(get(GET_PLAYER_STATISTICS_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .param("orderType", String.valueOf(PlayerStatisticsOrderType.BY_GAMES_PLAYED))
                .param("page", "1"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].playerName").value(TEST_REGISTERED_PLAYER_1.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].shotsFired").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].gamesPlayed").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].playerName").value(TEST_REGISTERED_PLAYER_2.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].shotsFired").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].gamesPlayed").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].playerName").value(TEST_REGISTERED_PLAYER_3.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].shotsFired").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[2].gamesPlayed").value(1));

        verify(playerStatisticsServiceMock).getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_GAMES_PLAYED, 1);
    }

    @Test
    public void testGetPlayerStatisticsAllOrderTypes() throws Exception {
        when(playerStatisticsServiceMock.getOrderedPlayerStatistics(any(PlayerStatisticsOrderType.class), any(Integer.class)))
                .thenReturn(Collections.emptyList());

        this.mockMvc.perform(get(GET_PLAYER_STATISTICS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderType", String.valueOf(PlayerStatisticsOrderType.BY_GAMES_PLAYED))
                        .param("page", "1"))
                .andExpect(status().isOk());

        verify(playerStatisticsServiceMock).getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_GAMES_PLAYED, 1);

        this.mockMvc.perform(get(GET_PLAYER_STATISTICS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderType", String.valueOf(PlayerStatisticsOrderType.BY_SHOTS_FIRED))
                        .param("page", "1"))
                .andExpect(status().isOk());

        verify(playerStatisticsServiceMock).getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_SHOTS_FIRED, 1);

        this.mockMvc.perform(get(GET_PLAYER_STATISTICS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderType", String.valueOf(PlayerStatisticsOrderType.BY_GAMES_WON_PERCENTAGE))
                        .param("page", "1"))
                .andExpect(status().isOk());

        verify(playerStatisticsServiceMock).getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_GAMES_WON_PERCENTAGE, 1);

        this.mockMvc.perform(get(GET_PLAYER_STATISTICS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderType", String.valueOf(PlayerStatisticsOrderType.BY_TRIPLE_20_HITS_PERCENTAGE))
                        .param("page", "1"))
                .andExpect(status().isOk());

        verify(playerStatisticsServiceMock).getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_TRIPLE_20_HITS_PERCENTAGE, 1);

        this.mockMvc.perform(get(GET_PLAYER_STATISTICS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderType", String.valueOf(PlayerStatisticsOrderType.BY_DOUBLE_20_HITS_PERCENTAGE))
                        .param("page", "1"))
                .andExpect(status().isOk());

        verify(playerStatisticsServiceMock).getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_DOUBLE_20_HITS_PERCENTAGE, 1);

        this.mockMvc.perform(get(GET_PLAYER_STATISTICS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderType", String.valueOf(PlayerStatisticsOrderType.BY_SINGLE_20_HITS_PERCENTAGE))
                        .param("page", "1"))
                .andExpect(status().isOk());

        verify(playerStatisticsServiceMock).getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_SINGLE_20_HITS_PERCENTAGE, 1);

        this.mockMvc.perform(get(GET_PLAYER_STATISTICS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderType", String.valueOf(PlayerStatisticsOrderType.BY_DOUBLE_25_HITS_PERCENTAGE))
                        .param("page", "1"))
                .andExpect(status().isOk());

        verify(playerStatisticsServiceMock).getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_DOUBLE_25_HITS_PERCENTAGE, 1);

        this.mockMvc.perform(get(GET_PLAYER_STATISTICS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderType", String.valueOf(PlayerStatisticsOrderType.BY_SINGLE_25_HITS_PERCENTAGE))
                        .param("page", "1"))
                .andExpect(status().isOk());

        verify(playerStatisticsServiceMock).getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_SINGLE_25_HITS_PERCENTAGE, 1);

        this.mockMvc.perform(get(GET_PLAYER_STATISTICS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderType", String.valueOf(PlayerStatisticsOrderType.BY_TRIPLE_HITS_PERCENTAGE))
                        .param("page", "1"))
                .andExpect(status().isOk());

        verify(playerStatisticsServiceMock).getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_TRIPLE_HITS_PERCENTAGE, 1);

        this.mockMvc.perform(get(GET_PLAYER_STATISTICS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderType", String.valueOf(PlayerStatisticsOrderType.BY_DOUBLE_HITS_PERCENTAGE))
                        .param("page", "1"))
                .andExpect(status().isOk());

        verify(playerStatisticsServiceMock).getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_DOUBLE_HITS_PERCENTAGE, 1);
    }

    @Test
    public void testGetPlayerStatisticsInvalidOrderType() throws Exception {
        this.mockMvc.perform(get(GET_PLAYER_STATISTICS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderType", INVALID_JSON_VALUE)
                        .param("page", "1"))
                .andExpect(status().isBadRequest());

        verify(playerStatisticsServiceMock, never()).getOrderedPlayerStatistics(any(PlayerStatisticsOrderType.class), any(Integer.class));
    }

    @Test
    public void testGetPlayerStatisticsInvalidPage() throws Exception {
        this.mockMvc.perform(get(GET_PLAYER_STATISTICS_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("orderType", String.valueOf(PlayerStatisticsOrderType.BY_GAMES_PLAYED))
                        .param("page", INVALID_JSON_VALUE))
                .andExpect(status().isBadRequest());

        verify(playerStatisticsServiceMock, never()).getOrderedPlayerStatistics(any(PlayerStatisticsOrderType.class), any(Integer.class));
    }

    @BeforeEach
    public void setup() {
        this.playerStatisticsServiceMock = mock(PlayerStatisticsService.class);
        this.playerStatisticsController = new PlayerStatisticsController(playerStatisticsServiceMock);
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.playerStatisticsController).build();
    }
}
