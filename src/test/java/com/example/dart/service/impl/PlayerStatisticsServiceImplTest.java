package com.example.dart.service.impl;

import com.example.dart.model.Game;
import com.example.dart.model.PlayerStatistics;
import com.example.dart.model.RegisteredPlayer;
import com.example.dart.model.Shot;
import com.example.dart.model.dto.PlayerStatisticsDto;
import com.example.dart.model.enums.GameState;
import com.example.dart.model.enums.PlayerStatisticsOrderType;
import com.example.dart.model.enums.ShotType;
import com.example.dart.model.exception.EntityNotFoundException;
import com.example.dart.repository.PlayerStatisticsRepository;
import com.example.dart.service.PlayerService;
import com.example.dart.service.PlayerStatisticsService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static utils.TestDataHolder.TEST_REGISTERED_PLAYER_1;

public class PlayerStatisticsServiceImplTest {

    @Mock
    private PlayerStatisticsRepository playerStatisticsRepositoryMock;
    @Mock
    private PlayerService playerServiceMock;
    private PlayerStatisticsService playerStatisticsService;

    private static final Shot TEST_SHOT = new Shot(20, ShotType.TRIPLE);
    private static final String TEST_PLAYER_NAME = "testPlayerName";
    private static final int TEST_PAGE = 1;

    @Test
    public void updatePlayerStatisticsTest() {
        Game game = mock(Game.class);
        RegisteredPlayer testRegisteredPlayer = mock(RegisteredPlayer.class);

        when(testRegisteredPlayer.getPlayerStatistics()).thenReturn(new PlayerStatistics());
        when(testRegisteredPlayer.getName()).thenReturn(TEST_PLAYER_NAME);

        when(game.getCurrentPlayer()).thenReturn(testRegisteredPlayer);
        when(game.getPlayers()).thenReturn(List.of(testRegisteredPlayer));
        when(game.getGameState()).thenReturn(GameState.FINISHED);

        doNothing().when(playerStatisticsRepositoryMock).updatePlayerStatistics(any(PlayerStatistics.class));

        playerStatisticsService.updatePlayerStatistics(game, TEST_SHOT);

        verify(playerStatisticsRepositoryMock, times(2))
                .updatePlayerStatistics(((RegisteredPlayer) game.getCurrentPlayer()).getPlayerStatistics());
    }

    @Test
    public void getOrderedPlayerStatisticsAllOrderTypesTest() {
        when(playerStatisticsRepositoryMock.getPageOfPlayerStatisticsOrderByGamesPlayedDesc(any(Integer.class))).thenReturn(Collections.emptyList());
        when(playerStatisticsRepositoryMock.getPageOfPlayerStatisticsOrderByShotsFiredDesc(any(Integer.class))).thenReturn(Collections.emptyList());
        when(playerStatisticsRepositoryMock.getPageOfPlayerStatisticsOrderByGamesWonPercentageDesc(any(Integer.class))).thenReturn(Collections.emptyList());
        when(playerStatisticsRepositoryMock.getPageOfPlayerStatisticsOrderByTriple20HitsPercentageDesc(any(Integer.class))).thenReturn(Collections.emptyList());
        when(playerStatisticsRepositoryMock.getPageOfPlayerStatisticsOrderByDouble20HitsPercentageDesc(any(Integer.class))).thenReturn(Collections.emptyList());
        when(playerStatisticsRepositoryMock.getPageOfPlayerStatisticsOrderBySingle20HitsPercentageDesc(any(Integer.class))).thenReturn(Collections.emptyList());
        when(playerStatisticsRepositoryMock.getPageOfPlayerStatisticsOrderByDouble25HitsPercentageDesc(any(Integer.class))).thenReturn(Collections.emptyList());
        when(playerStatisticsRepositoryMock.getPageOfPlayerStatisticsOrderBySingle25HitsPercentageDesc(any(Integer.class))).thenReturn(Collections.emptyList());
        when(playerStatisticsRepositoryMock.getPageOfPlayerStatisticsOrderByTripleHitsPercentageDesc(any(Integer.class))).thenReturn(Collections.emptyList());
        when(playerStatisticsRepositoryMock.getPageOfPlayerStatisticsOrderByDoubleHitsPercentageDesc(any(Integer.class))).thenReturn(Collections.emptyList());

        playerStatisticsService.getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_GAMES_PLAYED, TEST_PAGE);
        verify(playerStatisticsRepositoryMock, times(1)).getPageOfPlayerStatisticsOrderByGamesPlayedDesc(TEST_PAGE);

        playerStatisticsService.getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_SHOTS_FIRED, TEST_PAGE);
        verify(playerStatisticsRepositoryMock, times(1)).getPageOfPlayerStatisticsOrderByShotsFiredDesc(TEST_PAGE);

        playerStatisticsService.getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_GAMES_WON_PERCENTAGE, TEST_PAGE);
        verify(playerStatisticsRepositoryMock, times(1)).getPageOfPlayerStatisticsOrderByGamesWonPercentageDesc(TEST_PAGE);

        playerStatisticsService.getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_TRIPLE_20_HITS_PERCENTAGE, TEST_PAGE);
        verify(playerStatisticsRepositoryMock, times(1)).getPageOfPlayerStatisticsOrderByTriple20HitsPercentageDesc(TEST_PAGE);

        playerStatisticsService.getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_DOUBLE_20_HITS_PERCENTAGE, TEST_PAGE);
        verify(playerStatisticsRepositoryMock, times(1)).getPageOfPlayerStatisticsOrderByDouble20HitsPercentageDesc(TEST_PAGE);

        playerStatisticsService.getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_SINGLE_20_HITS_PERCENTAGE, TEST_PAGE);
        verify(playerStatisticsRepositoryMock, times(1)).getPageOfPlayerStatisticsOrderBySingle20HitsPercentageDesc(TEST_PAGE);

        playerStatisticsService.getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_DOUBLE_25_HITS_PERCENTAGE, TEST_PAGE);
        verify(playerStatisticsRepositoryMock, times(1)).getPageOfPlayerStatisticsOrderByDouble25HitsPercentageDesc(TEST_PAGE);

        playerStatisticsService.getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_SINGLE_25_HITS_PERCENTAGE, TEST_PAGE);
        verify(playerStatisticsRepositoryMock, times(1)).getPageOfPlayerStatisticsOrderBySingle25HitsPercentageDesc(TEST_PAGE);

        playerStatisticsService.getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_TRIPLE_HITS_PERCENTAGE, TEST_PAGE);
        verify(playerStatisticsRepositoryMock, times(1)).getPageOfPlayerStatisticsOrderByTripleHitsPercentageDesc(TEST_PAGE);

        playerStatisticsService.getOrderedPlayerStatistics(PlayerStatisticsOrderType.BY_DOUBLE_HITS_PERCENTAGE, TEST_PAGE);
        verify(playerStatisticsRepositoryMock, times(1)).getPageOfPlayerStatisticsOrderByDoubleHitsPercentageDesc(TEST_PAGE);
    }

    @Test
    public void testGetPlayerStatisticsByPlayerName() {
        when(playerStatisticsRepositoryMock.getPlayerStatisticsByPlayerName(any(String.class))).thenReturn(Optional.of(new PlayerStatistics(TEST_REGISTERED_PLAYER_1)));

        PlayerStatisticsDto playerStatisticsDto = playerStatisticsService.getPlayerStatisticsByPlayerName(TEST_REGISTERED_PLAYER_1.getName());

        verify(playerStatisticsRepositoryMock, times(1)).getPlayerStatisticsByPlayerName(TEST_REGISTERED_PLAYER_1.getName());

        assertEquals(playerStatisticsDto.getPlayerName(), TEST_REGISTERED_PLAYER_1.getName());
    }

    @Test
    public void testGetPlayerStatisticsByPlayerNameWithUnexistentName() {
        when(playerStatisticsRepositoryMock.getPlayerStatisticsByPlayerName(any(String.class))).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerStatisticsService.getPlayerStatisticsByPlayerName(TEST_REGISTERED_PLAYER_1.getName()));

        verify(playerStatisticsRepositoryMock, times(1)).getPlayerStatisticsByPlayerName(TEST_REGISTERED_PLAYER_1.getName());
    }

    @BeforeEach
    public void prepareTestEnvironment() {
        this.playerStatisticsRepositoryMock = mock(PlayerStatisticsRepository.class);
        this.playerServiceMock = mock(PlayerService.class);
        this.playerStatisticsService = new PlayerStatisticsServiceImpl(playerStatisticsRepositoryMock, playerServiceMock);
    }
}
