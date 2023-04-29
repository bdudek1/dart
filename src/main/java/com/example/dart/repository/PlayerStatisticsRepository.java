package com.example.dart.repository;

import com.example.dart.model.PlayerStatistics;

import java.util.List;

public interface PlayerStatisticsRepository {
    public static final int PAGE_SIZE = 10;
    public void updatePlayerStatistics(PlayerStatistics playerStatistics);
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
