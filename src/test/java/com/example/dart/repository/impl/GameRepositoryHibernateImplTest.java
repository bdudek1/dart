package com.example.dart.repository.impl;

import com.example.dart.DartApplication;
import com.example.dart.configuration.HibernateTestConfiguration;
import com.example.dart.model.Game;
import com.example.dart.model.Shot;
import com.example.dart.model.enums.ShotType;
import com.example.dart.repository.GameRepository;
import com.example.dart.repository.PlayerRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestDataHolder.TEST_PLAYERS;

@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest(classes = {DartApplication.class, HibernateTestConfiguration.class})
public class GameRepositoryHibernateImplTest {

    private static boolean arePlayersInitialized = false;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void testCreateGameAndFindById() {
        Game testGame = new Game(TEST_PLAYERS);
        gameRepository.saveGame(testGame);

        Long id = testGame.getId();
        Optional<Game> testGameFromRepository = gameRepository.findGameById(id);

        assertTrue(testGameFromRepository.isPresent());

        gameRepository.deleteGame(testGame);
    }

    @Test
    public void testUpdateGame() {
        Game testGame = new Game(TEST_PLAYERS);
        gameRepository.saveGame(testGame);

        Long id = testGame.getId();

        assertEquals(testGame.getShotsFiredInTurn().size(), 0);

        testGame.performShot(new Shot(20, ShotType.TRIPLE));
        gameRepository.updateGame(testGame);

        Optional<Game> testGameFromRepositoryAfterShot = gameRepository.findGameById(id);

        assertTrue(testGameFromRepositoryAfterShot.isPresent());
        assertEquals(testGameFromRepositoryAfterShot.get().getShotsFiredInTurn().size(), 1);

        gameRepository.deleteGame(testGame);
    }

    @Test
    public void deleteGameTest() {
        Game testGame = new Game(TEST_PLAYERS);
        gameRepository.saveGame(testGame);

        Long id = testGame.getId();

        gameRepository.deleteGame(testGame);

        Optional<Game> testGameFromRepository = gameRepository.findGameById(id);

        assertTrue(testGameFromRepository.isEmpty());
    }

    @Test
    public void testFindGamesByPlayerId() {
        Game testGame = new Game(TEST_PLAYERS);
        gameRepository.saveGame(testGame);

        Long playerId = testGame.getPlayers().get(0).getId();

        Collection<Game> testGamesFromRepository = gameRepository.findGamesByPlayerId(playerId);

        assertTrue(testGamesFromRepository.size() > 0);

        gameRepository.deleteGame(testGame);
    }

    @BeforeEach
    public void saveTestPlayers() {
        if (!arePlayersInitialized) {
            TEST_PLAYERS.forEach(playerRepository::savePlayer);
            arePlayersInitialized = true;
        }
    }

}
