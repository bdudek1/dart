package com.example.dart.service.impl;

import com.example.dart.model.Game;
import com.example.dart.model.Shot;
import com.example.dart.model.dto.GameDto;
import com.example.dart.model.enums.ShotType;
import com.example.dart.model.exception.EntityNotFoundException;
import com.example.dart.model.exception.GameNotInProgressException;
import com.example.dart.model.exception.NotEnoughPlayersException;
import com.example.dart.repository.GameRepository;
import com.example.dart.service.GameService;

import com.example.dart.service.PlayerStatisticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static utils.TestDataHolder.TEST_PLAYERS;
import static utils.TestDataHolder.TEST_PLAYER_1;

public class GameServiceImplTest {
    //TODO:: update the tests after implementing PlayerStatisticsService
    @Mock
    private GameRepository gameRepositoryMock;
    @Mock
    private PlayerStatisticsService playerStatisticsServiceMock;
    private GameService gameService;
    private static Game TEST_GAME = new Game(TEST_PLAYERS);

    @Test
    public void createGameTest() {
        doNothing().when(gameRepositoryMock).saveGame(any(Game.class));
        Game createdGame = gameService.createGame(TEST_PLAYERS);

        assertEquals(createdGame.getPlayers(), TEST_PLAYERS);
        verify(gameRepositoryMock, times(1)).saveGame(any(Game.class));
    }

    @Test
    public void createGameWithPlayersLessThanTwoShouldThrowException() {
        doNothing().when(gameRepositoryMock).saveGame(any(Game.class));
        assertThrows(NotEnoughPlayersException.class, () -> gameService.createGame(List.of(TEST_PLAYER_1)));

        verify(gameRepositoryMock, times(0)).saveGame(any(Game.class));
    }

    @Test
    public void testFindGameById() {
        when(gameRepositoryMock.findGameById(anyLong())).thenReturn(Optional.of(TEST_GAME));
        Game foundGame = gameService.findGameById(1L);

        assertEquals(foundGame, TEST_GAME);
        verify(gameRepositoryMock, times(1)).findGameById(anyLong());
    }

    @Test
    public void testFindGameByIdWithNonExistingId() {
        when(gameRepositoryMock.findGameById(anyLong())).thenReturn(Optional.empty());
        assertThrows(EntityNotFoundException.class, () -> gameService.findGameById(1L));

        verify(gameRepositoryMock, times(1)).findGameById(anyLong());
    }

    @Test
    public void testFindGamesByPlayerId() {
        when(gameRepositoryMock.findGamesByPlayerId(anyLong())).thenReturn(List.of(TEST_GAME));
        Collection<Game> foundGames = gameService.findGamesByPlayerId(1L);

        assertEquals(foundGames, List.of(TEST_GAME));
        verify(gameRepositoryMock, times(1)).findGamesByPlayerId(anyLong());
    }

    @Test
    public void testAddShotAndReturnGameDto() {
        doNothing().when(gameRepositoryMock).updateGame(any(Game.class));
        doNothing().when(playerStatisticsServiceMock).updatePlayerStatistics(any(Game.class), any(Shot.class));

        when(gameRepositoryMock.findGameById(anyLong())).thenReturn(Optional.of(TEST_GAME));
        GameDto gameDto = gameService.addShotAndReturnGameDto(1L, new Shot(20, ShotType.DOUBLE));

        Collection<Integer> playerScoresFromGameDto = new ArrayList<>(gameDto.getPlayerScoresMap().values());
        Collection<Integer> playerScoresFromTestGame = new ArrayList<>(TEST_GAME.getPlayerScoresMap().values());

        assertEquals(playerScoresFromGameDto, playerScoresFromTestGame);
        assertEquals(gameDto.getGameState(), TEST_GAME.getGameState());

        verify(gameRepositoryMock, times(1)).findGameById(anyLong());
        verify(gameRepositoryMock, times(1)).updateGame(any(Game.class));
        verify(playerStatisticsServiceMock, times(1)).updatePlayerStatistics(any(Game.class), any(Shot.class));
    }

    @Test
    public void testAddShotAndReturnGameDtoShouldThrowExceptionOnFinishedGame() {
        doNothing().when(gameRepositoryMock).updateGame(any(Game.class));
        when(gameRepositoryMock.findGameById(anyLong())).thenReturn(Optional.of(TEST_GAME));

        Shot mockedShot = mock(Shot.class);
        when(mockedShot.getScore()).thenReturn(501);

        gameService.addShotAndReturnGameDto(1L, mockedShot);
        //game is finished at this point as the 501 score shot made first player won instantly

        assertThrows(GameNotInProgressException.class, () -> gameService.addShotAndReturnGameDto(1L, mockedShot));

        verify(gameRepositoryMock, times(2)).findGameById(anyLong());
        verify(gameRepositoryMock, times(1)).updateGame(any(Game.class));
    }

    @BeforeEach
    public void prepareTestEnvironment() {
        this.gameRepositoryMock = mock(GameRepository.class);
        this.playerStatisticsServiceMock = mock(PlayerStatisticsService.class);
        this.gameService = new GameServiceImpl(gameRepositoryMock, playerStatisticsServiceMock);
        TEST_GAME = new Game(TEST_PLAYERS);
    }
}
