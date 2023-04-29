package com.example.dart.service.impl;

import com.example.dart.model.*;
import com.example.dart.model.dto.PlayerStatisticsDto;
import com.example.dart.model.enums.GameState;
import com.example.dart.model.enums.PlayerStatisticsOrderType;
import com.example.dart.repository.PlayerStatisticsRepository;
import com.example.dart.service.PlayerStatisticsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PlayerStatisticsServiceImpl implements PlayerStatisticsService {
    private static final Logger logger = LoggerFactory.getLogger(PlayerStatisticsServiceImpl.class);
    private final PlayerStatisticsRepository playerStatisticsRepository;

    @Autowired
    public PlayerStatisticsServiceImpl(PlayerStatisticsRepository playerStatisticsRepository) {
        this.playerStatisticsRepository = playerStatisticsRepository;
    }

    @Override
    public void updatePlayerStatistics(Game game, Shot shot) {
        Player player = game.getCurrentPlayer();
        if (player instanceof RegisteredPlayer registeredPlayer) {
            PlayerStatistics playerStatistics = registeredPlayer.getPlayerStatistics();

            addShotToPlayerStatistics(playerStatistics, shot);

            playerStatisticsRepository.updatePlayerStatistics(playerStatistics);
        }

        if (game.getGameState() == GameState.FINISHED) {
            addGamesPlayedToPlayerStatistics(game);
        }
    }

    @Override
    public List<PlayerStatisticsDto> getOrderedPlayerStatistics(PlayerStatisticsOrderType orderType, int page) {
        logger.info("Getting ordered player statistics, order type: {}, page: {}", orderType, page);
        List<PlayerStatistics> playerStatistics;

        switch (orderType) {
            case BY_GAMES_PLAYED -> {
                playerStatistics = playerStatisticsRepository.getPageOfPlayerStatisticsOrderByGamesPlayedDesc(page);
            }
            case BY_SHOTS_FIRED -> {
                playerStatistics = playerStatisticsRepository.getPageOfPlayerStatisticsOrderByShotsFiredDesc(page);
            }
            case BY_GAMES_WON_PERCENTAGE -> {
                playerStatistics = playerStatisticsRepository.getPageOfPlayerStatisticsOrderByGamesWonPercentageDesc(page);
            }
            case BY_TRIPLE_20_HITS_PERCENTAGE -> {
                playerStatistics = playerStatisticsRepository.getPageOfPlayerStatisticsOrderByTriple20HitsPercentageDesc(page);
            }
            case BY_DOUBLE_20_HITS_PERCENTAGE -> {
                playerStatistics = playerStatisticsRepository.getPageOfPlayerStatisticsOrderByDouble20HitsPercentageDesc(page);
            }
            case BY_SINGLE_20_HITS_PERCENTAGE -> {
                playerStatistics = playerStatisticsRepository.getPageOfPlayerStatisticsOrderBySingle20HitsPercentageDesc(page);
            }
            case BY_DOUBLE_25_HITS_PERCENTAGE -> {
                playerStatistics = playerStatisticsRepository.getPageOfPlayerStatisticsOrderByDouble25HitsPercentageDesc(page);
            }
            case BY_SINGLE_25_HITS_PERCENTAGE -> {
                playerStatistics = playerStatisticsRepository.getPageOfPlayerStatisticsOrderBySingle25HitsPercentageDesc(page);
            }
            case BY_TRIPLE_HITS_PERCENTAGE -> {
                playerStatistics = playerStatisticsRepository.getPageOfPlayerStatisticsOrderByTripleHitsPercentageDesc(page);
            }
            case BY_DOUBLE_HITS_PERCENTAGE -> {
                playerStatistics = playerStatisticsRepository.getPageOfPlayerStatisticsOrderByDoubleHitsPercentageDesc(page);
            }
            default -> {
                logger.error("Unknown order type: {}", orderType);
                playerStatistics = Collections.emptyList();
            }
        }

        return new PlayerStatisticsDto().playerStatisticsListToPlayerStatisticsListDto(playerStatistics);
    }

    private void addShotToPlayerStatistics(PlayerStatistics playerStatistics, Shot shot) {
        playerStatistics.incrementShotsFired();

        switch (shot.getShotType()) {
            case TRIPLE -> playerStatistics.incrementTripleHits();
            case DOUBLE -> playerStatistics.incrementDoubleHits();
        }

        switch (shot.getScore()) {
            case 20 -> playerStatistics.incrementSingle20Hits();
            case 40 -> playerStatistics.incrementDouble20Hits();
            case 60 -> playerStatistics.incrementTriple20Hits();
            case 25 -> playerStatistics.incrementSingle25Hits();
            case 50 -> playerStatistics.incrementDouble25Hits();
        }
    }

    private void addGamesPlayedToPlayerStatistics(Game game) {
        Set<Player> players = new HashSet<>(game.getPlayers());

        players.forEach(player -> {
            if (player instanceof RegisteredPlayer registeredPlayer) {
                PlayerStatistics playerStatistics = registeredPlayer.getPlayerStatistics();
                playerStatistics.incrementGamesPlayed();

                if (player.getName().equals(game.getWinner())) {
                    playerStatistics.incrementGamesWon();
                }

                playerStatisticsRepository.updatePlayerStatistics(playerStatistics);
            }
        });
    }
}
