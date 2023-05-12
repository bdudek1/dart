package com.example.dart.repository.impl;

import com.example.dart.DartApplication;
import com.example.dart.configuration.HibernateTestConfiguration;
import com.example.dart.model.Player;
import com.example.dart.model.RegisteredPlayer;
import com.example.dart.repository.PlayerRepository;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static utils.TestDataHolder.*;

@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest(classes = {DartApplication.class, HibernateTestConfiguration.class})
public class PlayerRepositoryHibernateImplTest {

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void testSavePlayer() {
        Player testPlayer = new Player(TEST_PLAYER_NAME_1);

        playerRepository.savePlayer(testPlayer);
        Collection<Player> allPlayers = playerRepository.findAllPlayers();

        assertTrue(allPlayers.size() > 0);

        playerRepository.deletePlayer(testPlayer);
    }

    @Test
    public void testGetPlayerByName() {
        Player testPlayer = new Player(TEST_PLAYER_NAME_1);

        playerRepository.savePlayer(testPlayer);
        Optional<Player> player = playerRepository.findPlayerByName(TEST_PLAYER_NAME_1);

        assertTrue(player.isPresent());
        assertEquals(TEST_PLAYER_NAME_1, player.get().getName());

        playerRepository.deletePlayer(testPlayer);
    }

    @Test
    public void testGetAllPlayers() {
        Player testPlayer = new Player(TEST_PLAYER_NAME_1);
        Player testPlayer2 = new Player(TEST_PLAYER_NAME_2);

        playerRepository.savePlayer(testPlayer);
        playerRepository.savePlayer(testPlayer2);
        Collection<Player> allPlayers = playerRepository.findAllPlayers();

        assertEquals(2, allPlayers.size());

        playerRepository.deletePlayer(testPlayer);
        playerRepository.deletePlayer(testPlayer2);
    }

    @Test
    public void testDeletePlayer() {
        Player testPlayer = new Player(TEST_PLAYER_NAME_1);

        playerRepository.savePlayer(testPlayer);
        Collection<Player> allPlayers = playerRepository.findAllPlayers();

        assertTrue(allPlayers.size() > 0);
        playerRepository.deletePlayer(testPlayer);

        Collection<Player> allPlayersAfterDelete = playerRepository.findAllPlayers();

        assertTrue(allPlayersAfterDelete.size() == 0);
    }

    @Test
    public void testValidatePlayerCredentials() {
        RegisteredPlayer testPlayer = new RegisteredPlayer(TEST_PLAYER_NAME_1, TEST_PASSWORD);

        playerRepository.savePlayer(testPlayer);

        assertTrue(playerRepository.arePlayerCredentialsValid(TEST_PLAYER_NAME_1, String.valueOf(TEST_PASSWORD)));

        playerRepository.deletePlayer(testPlayer);
    }

    @Test
    public void testValidatePlayerCredentialsInvalidPassword() {
        RegisteredPlayer testPlayer = new RegisteredPlayer(TEST_PLAYER_NAME_1, TEST_PASSWORD);

        playerRepository.savePlayer(testPlayer);

        assertFalse(playerRepository.arePlayerCredentialsValid(TEST_PLAYER_NAME_1, "invalid"));

        playerRepository.deletePlayer(testPlayer);
    }

    @Test
    public void testValidatePlayerCredentialsInvalidName() {
        RegisteredPlayer testPlayer = new RegisteredPlayer(TEST_PLAYER_NAME_1, TEST_PASSWORD);

        playerRepository.savePlayer(testPlayer);

        assertFalse(playerRepository.arePlayerCredentialsValid("invalid", String.valueOf(TEST_PASSWORD)));

        playerRepository.deletePlayer(testPlayer);
    }

    @Test
    public void testValidatePlayerCredentialsGuestPlayer() {
        Player testPlayer = new Player(TEST_PLAYER_NAME_1);

        playerRepository.savePlayer(testPlayer);

        assertFalse(playerRepository.arePlayerCredentialsValid(TEST_PLAYER_NAME_1, String.valueOf(TEST_PASSWORD)));

        playerRepository.deletePlayer(testPlayer);
    }
}
