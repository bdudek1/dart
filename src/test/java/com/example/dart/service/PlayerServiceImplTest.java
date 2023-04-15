package com.example.dart.service;

import com.example.dart.model.Player;
import com.example.dart.model.exception.EntityAlreadyExistsException;
import com.example.dart.model.exception.EntityNotFoundException;
import com.example.dart.repository.PlayerRepository;
import com.example.dart.service.impl.PlayerServiceImpl;

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

public class PlayerServiceImplTest {

    @Mock
    private PlayerRepository playerRepositoryMock;
    private PlayerService playerService;
    private static final Player TEST_PLAYER = new Player("testPlayer");

    @Test
    public void createPlayerTest() {
        doNothing().when(playerRepositoryMock).savePlayer(any());

        Player savedPlayer = playerService.createPlayer(TEST_PLAYER);

        verify(playerRepositoryMock, times(1)).savePlayer(any());
        assertEquals(TEST_PLAYER, savedPlayer);
    }

    @Test
    public void createPlayerShouldThrowEntityAlreadyExistsExceptionTest() {
        doThrow(new DataIntegrityViolationException("testMessage")).when(playerRepositoryMock).savePlayer(any());

        assertThrows(EntityAlreadyExistsException.class, () -> playerService.createPlayer(TEST_PLAYER));

        verify(playerRepositoryMock, times(1)).savePlayer(any());
    }

    @Test
    public void findPlayerByIdTest() {
        when(playerRepositoryMock.findPlayerById(anyInt())).thenReturn(Optional.of(TEST_PLAYER));

        Player foundPlayer = playerService.findPlayerById(1);

        verify(playerRepositoryMock, times(1)).findPlayerById(anyInt());
        assertEquals(TEST_PLAYER, foundPlayer);
    }

    @Test
    public void findPlayerByIdShouldThrowEntityNotFoundExceptionTest() {
        when(playerRepositoryMock.findPlayerById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> playerService.findPlayerById(1));

        verify(playerRepositoryMock, times(1)).findPlayerById(anyInt());
    }

    @Test
    public void findAllPlayersTest() {
        Collection<Player> playersTestCollection = Collections.singletonList(TEST_PLAYER);

        when(playerRepositoryMock.findAllPlayers()).thenReturn(playersTestCollection);

        Collection<Player> foundPlayers = playerService.findAllPlayers();

        verify(playerRepositoryMock, times(1)).findAllPlayers();
        assertEquals(playersTestCollection, foundPlayers);
        assertEquals(1, foundPlayers.size());
    }

    @BeforeEach
    public void prepareTestEnvironment() {
        this.playerRepositoryMock = mock(PlayerRepository.class);
        this.playerService = new PlayerServiceImpl(playerRepositoryMock);
    }

}
