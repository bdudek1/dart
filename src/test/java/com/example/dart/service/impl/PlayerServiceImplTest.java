package com.example.dart.service.impl;

import com.example.dart.model.Player;
import com.example.dart.model.exception.EntityAlreadyExistsException;
import com.example.dart.model.exception.EntityNotFoundException;
import com.example.dart.repository.PlayerRepository;
import com.example.dart.service.PlayerService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mock;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import static utils.TestDataHolder.TEST_PLAYER_1;

public class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepositoryMock;
    private PlayerService playerService;

    @Test
    public void createPlayerTest() {
        doNothing().when(playerRepositoryMock).savePlayer(any());

        Player savedPlayer = playerService.createPlayer(TEST_PLAYER_1);

        verify(playerRepositoryMock, times(1)).savePlayer(any());
        assertEquals(TEST_PLAYER_1, savedPlayer);
    }

    @Test
    public void createPlayerShouldThrowEntityAlreadyExistsExceptionTest() {
        doThrow(new DataIntegrityViolationException("testMessage")).when(playerRepositoryMock).savePlayer(any());

        assertThrows(EntityAlreadyExistsException.class, () -> playerService.createPlayer(TEST_PLAYER_1));

        verify(playerRepositoryMock, times(1)).savePlayer(any());
    }

    @Test
    public void findPlayerByIdTest() {
        when(playerRepositoryMock.findPlayerById(anyInt())).thenReturn(Optional.of(TEST_PLAYER_1));

        Player foundPlayer = playerService.findPlayerById(1);

        verify(playerRepositoryMock, times(1)).findPlayerById(anyInt());
        assertEquals(TEST_PLAYER_1, foundPlayer);
    }

    @Test
    public void findPlayerByIdShouldThrowEntityNotFoundExceptionTest() {
        when(playerRepositoryMock.findPlayerById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerService.findPlayerById(1));

        verify(playerRepositoryMock, times(1)).findPlayerById(anyInt());
    }

    @Test
    public void findPlayerByNameTest() {
        when(playerRepositoryMock.findPlayerByName(anyString())).thenReturn(Optional.of(TEST_PLAYER_1));

        Player foundPlayer = playerService.findPlayerByName(TEST_PLAYER_1.getName());

        verify(playerRepositoryMock, times(1)).findPlayerByName(anyString());
        assertEquals(TEST_PLAYER_1, foundPlayer);
    }

    @Test
    public void findPlayerByNameShouldThrowEntityNotFoundExceptionTest() {
        when(playerRepositoryMock.findPlayerByName(anyString())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerService.findPlayerByName(TEST_PLAYER_1.getName()));

        verify(playerRepositoryMock, times(1)).findPlayerByName(anyString());
    }

    @Test
    public void findAllPlayersTest() {
        Collection<Player> playersTestCollection = Collections.singletonList(TEST_PLAYER_1);

        when(playerRepositoryMock.findAllPlayers()).thenReturn(playersTestCollection);

        Collection<Player> foundPlayers = playerService.findAllPlayers();

        verify(playerRepositoryMock, times(1)).findAllPlayers();
        assertEquals(playersTestCollection, foundPlayers);
        assertEquals(1, foundPlayers.size());
    }

    @Test
    public void deletePlayerTest() {
        doNothing().when(playerRepositoryMock).deletePlayer(any(Player.class));

        playerService.deletePlayer(TEST_PLAYER_1);

        verify(playerRepositoryMock, times(1)).deletePlayer(any(Player.class));
    }

    @BeforeEach
    public void prepareTestEnvironment() {
        this.playerRepositoryMock = mock(PlayerRepository.class);
        this.playerService = new PlayerServiceImpl(playerRepositoryMock);
    }

}
