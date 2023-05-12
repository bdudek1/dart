package com.example.dart.repository;

import com.example.dart.model.PlayerStatistics;

import java.util.List;
import java.util.Optional;

public interface PlayerStatisticsRepository {
    int PAGE_SIZE = 10;
    void updatePlayerStatistics(PlayerStatistics playerStatistics);
    Optional<PlayerStatistics> getPlayerStatisticsByPlayerName(String playerName);
    List<PlayerStatistics> getPageOfPlayerStatisticsOrderByGamesPlayedDesc(int page);
    List<PlayerStatistics> getPageOfPlayerStatisticsOrderByShotsFiredDesc(int page);
    List<PlayerStatistics> getPageOfPlayerStatisticsOrderByGamesWonPercentageDesc(int page);
    List<PlayerStatistics> getPageOfPlayerStatisticsOrderByTriple20HitsPercentageDesc(int page);
    List<PlayerStatistics> getPageOfPlayerStatisticsOrderByDouble20HitsPercentageDesc(int page);
    List<PlayerStatistics> getPageOfPlayerStatisticsOrderBySingle20HitsPercentageDesc(int page);
    List<PlayerStatistics> getPageOfPlayerStatisticsOrderBySingle25HitsPercentageDesc(int page);
    List<PlayerStatistics> getPageOfPlayerStatisticsOrderByDouble25HitsPercentageDesc(int page);
    List<PlayerStatistics> getPageOfPlayerStatisticsOrderByDoubleHitsPercentageDesc(int page);
    List<PlayerStatistics> getPageOfPlayerStatisticsOrderByTripleHitsPercentageDesc(int page);
}
