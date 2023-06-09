package com.example.dart.repository.impl;

import com.example.dart.DartApplication;
import com.example.dart.configuration.HibernateTestConfiguration;
import com.example.dart.model.PlayerStatistics;
import com.example.dart.model.RegisteredPlayer;
import com.example.dart.repository.PlayerRepository;
import com.example.dart.repository.PlayerStatisticsRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static utils.TestDataHolder.*;

@PropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
@SpringBootTest(classes = {DartApplication.class, HibernateTestConfiguration.class})
public class PlayerStatisticsRepositoryHibernateImplTest {

    @Autowired
    private PlayerStatisticsRepository playerStatisticsRepository;
    @Autowired
    private PlayerRepository playerRepository;

    private static boolean initialized = false;

    @Test
    public void updatePlayerStatisticsTest() {
        List<PlayerStatistics> playerStatisticsList = playerStatisticsRepository.getPageOfPlayerStatisticsOrderByGamesPlayedDesc(1);
        PlayerStatistics playerStatistics = playerStatisticsList.get(0);

        assertEquals(0, playerStatistics.getGamesPlayed());
        assertEquals(0, playerStatistics.getGamesWon());
        assertEquals(0, playerStatistics.getShotsFired());
        assertEquals(0, playerStatistics.getTriple20Hits());
        assertEquals(0, playerStatistics.getDouble20Hits());
        assertEquals(0, playerStatistics.getSingle20Hits());
        assertEquals(0, playerStatistics.getSingle25Hits());
        assertEquals(0, playerStatistics.getDouble25Hits());
        assertEquals(0, playerStatistics.getDoubleHits());
        assertEquals(0, playerStatistics.getTripleHits());

        playerStatistics.incrementGamesPlayed();
        playerStatistics.incrementGamesWon();
        playerStatistics.incrementShotsFired();
        playerStatistics.incrementTriple20Hits();
        playerStatistics.incrementDouble20Hits();
        playerStatistics.incrementSingle20Hits();
        playerStatistics.incrementSingle25Hits();
        playerStatistics.incrementDouble25Hits();
        playerStatistics.incrementDoubleHits();
        playerStatistics.incrementTripleHits();

        playerStatisticsRepository.updatePlayerStatistics(playerStatistics);

        playerStatisticsList = playerStatisticsRepository.getPageOfPlayerStatisticsOrderByGamesPlayedDesc(1);
        playerStatistics = playerStatisticsList.get(0);

        assertEquals(1, playerStatistics.getGamesPlayed());
        assertEquals(1, playerStatistics.getGamesWon());
        assertEquals(1, playerStatistics.getShotsFired());
        assertEquals(1, playerStatistics.getTriple20Hits());
        assertEquals(1, playerStatistics.getDouble20Hits());
        assertEquals(1, playerStatistics.getSingle20Hits());
        assertEquals(1, playerStatistics.getSingle25Hits());
        assertEquals(1, playerStatistics.getDouble25Hits());
        assertEquals(1, playerStatistics.getDoubleHits());
        assertEquals(1, playerStatistics.getTripleHits());
    }

    /*
      The expected value of 1 in most cases below is because the Repository will not select
      the playerStatistics in those queries if the gamesPlayed or shotsFired are equal to 0,
      that is because while sorting results by percentages mentioned in method names the query will
      force the DBMS to divide by zero
     */
    @Test
    public void testGetPlayerStatisticsByAllOrders() {
        assertEquals(3, playerStatisticsRepository.getPageOfPlayerStatisticsOrderByGamesPlayedDesc(1).size());
        assertEquals(3, playerStatisticsRepository.getPageOfPlayerStatisticsOrderByShotsFiredDesc(1).size());
        assertEquals(1, playerStatisticsRepository.getPageOfPlayerStatisticsOrderByGamesWonPercentageDesc(1).size());
        assertEquals(1, playerStatisticsRepository.getPageOfPlayerStatisticsOrderByTriple20HitsPercentageDesc(1).size());
        assertEquals(1, playerStatisticsRepository.getPageOfPlayerStatisticsOrderByDouble20HitsPercentageDesc(1).size());
        assertEquals(1, playerStatisticsRepository.getPageOfPlayerStatisticsOrderBySingle20HitsPercentageDesc(1).size());
        assertEquals(1, playerStatisticsRepository.getPageOfPlayerStatisticsOrderBySingle25HitsPercentageDesc(1).size());
        assertEquals(1, playerStatisticsRepository.getPageOfPlayerStatisticsOrderByDouble25HitsPercentageDesc(1).size());
        assertEquals(1, playerStatisticsRepository.getPageOfPlayerStatisticsOrderByDoubleHitsPercentageDesc(1).size());
        assertEquals(1, playerStatisticsRepository.getPageOfPlayerStatisticsOrderByTripleHitsPercentageDesc(1).size());
    }

    @Test
    public void testGetPlayerStatisticsUnexistentPage() {
        assertEquals(3, playerStatisticsRepository.getPageOfPlayerStatisticsOrderByGamesPlayedDesc(1).size());
        assertEquals(0, playerStatisticsRepository.getPageOfPlayerStatisticsOrderByGamesPlayedDesc(2).size());
    }

    @Test
    public void testGetPlayerStatisticsByPlayerName() {
        assertNotNull(playerStatisticsRepository.getPlayerStatisticsByPlayerName(TEST_REGISTERED_PLAYER_1.getName()));
        assertNotNull(playerStatisticsRepository.getPlayerStatisticsByPlayerName(TEST_REGISTERED_PLAYER_2.getName()));
        assertNotNull(playerStatisticsRepository.getPlayerStatisticsByPlayerName(TEST_REGISTERED_PLAYER_3.getName()));
    }

    @Test
    public void testGetPlayerStatisticsByPlayerNameUnexistent() {
        assertEquals(Optional.empty(), playerStatisticsRepository.getPlayerStatisticsByPlayerName("unexistent"));
    }

    @BeforeEach
    public void setup() {
        if (initialized) {
            return;
        }

        RegisteredPlayer registeredPlayer = TEST_REGISTERED_PLAYER_1;
        registeredPlayer.setPlayerStatistics(new PlayerStatistics(registeredPlayer));

        RegisteredPlayer registeredPlayer2 = TEST_REGISTERED_PLAYER_2;
        registeredPlayer2.setPlayerStatistics(new PlayerStatistics(registeredPlayer2));

        RegisteredPlayer registeredPlayer3 = TEST_REGISTERED_PLAYER_3;
        registeredPlayer3.setPlayerStatistics(new PlayerStatistics(registeredPlayer3));

        playerRepository.savePlayer(registeredPlayer);
        playerRepository.savePlayer(registeredPlayer2);
        playerRepository.savePlayer(registeredPlayer3);

        initialized = true;
    }
}
