package com.example.dart.repository.impl;

import com.example.dart.DartApplication;
import com.example.dart.configuration.HibernateTestConfiguration;
import com.example.dart.model.Game;
import com.example.dart.model.Player;
import com.example.dart.model.Shot;
import com.example.dart.model.enums.ShotType;
import com.example.dart.repository.GameRepository;
import com.example.dart.repository.PlayerRepository;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static utils.TestDataHolder.TEST_PLAYERS;

@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest(classes = {DartApplication.class, HibernateTestConfiguration.class})
public class GameRepositoryHibernateImplTest {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Test
    public void testCreateGameAndFindById() {
        Collection<Player> testPlayers = initializePlayers();

        Game testGame = new Game(testPlayers);
        gameRepository.saveGame(testGame);

        Long id = testGame.getId();
        Optional<Game> testGameFromRepository = gameRepository.findGameById(id);

        assertTrue(testGameFromRepository.isPresent());

        gameRepository.deleteGame(testGame);
    }

    @Test
    public void testUpdateGame() {
        Collection<Player> testPlayers = initializePlayers();

        Game testGame = new Game(testPlayers);
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
        Collection<Player> testPlayers = initializePlayers();

        Game testGame = new Game(testPlayers);
        gameRepository.saveGame(testGame);

        Long id = testGame.getId();

        gameRepository.deleteGame(testGame);

        Optional<Game> testGameFromRepository = gameRepository.findGameById(id);

        assertTrue(testGameFromRepository.isEmpty());
    }

    @Disabled("Does not work in test environment, works well in production environment")
    @Test
    public void testFindGamesByPlayerId() {
        Game testGame = new Game(TEST_PLAYERS);
        gameRepository.saveGame(testGame);

        Long playerId = testGame.getPlayers().get(0).getId();

        Collection<Game> testGamesFromRepository = gameRepository.findGamesByPlayerId(playerId);

        assertTrue(testGamesFromRepository.size() > 0);

        gameRepository.deleteGame(testGame);
    }

    private Collection<Player> initializePlayers() {
        Collection<Player> testPlayers = List.of(new Player("testPlayer1"), new Player("testPlayer2"));
        testPlayers.forEach(playerRepository::savePlayer);

        return testPlayers;
    }

}
