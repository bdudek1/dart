package com.example.dart.model.dto;

import com.example.dart.model.PlayerStatistics;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;
import java.util.stream.Collectors;

@ToString
@Getter
@NoArgsConstructor
public class PlayerStatisticsDto {
    private String playerName;
    private int gamesPlayed;
    private int shotsFired;
    private double gamesWonPercentage;
    private double triple20HitsPercentage;
    private double double20HitsPercentage;
    private double single20HitsPercentage;
    private double single25HitsPercentage;
    private double double25HitsPercentage;
    private double doubleHitsPercentage;
    private double tripleHitsPercentage;

    public PlayerStatisticsDto(PlayerStatistics playerStatistics) {
        this.playerName = playerStatistics.getPlayer().getName();
        this.gamesPlayed = (int) playerStatistics.getGamesPlayed();
        this.shotsFired = (int) playerStatistics.getShotsFired();
        this.gamesWonPercentage = Math.round((double) playerStatistics.getGamesWon() / gamesPlayed * 100);
        this.triple20HitsPercentage = Math.round((double) playerStatistics.getTriple20Hits() / shotsFired * 100);
        this.double20HitsPercentage = Math.round((double) playerStatistics.getDouble20Hits() / shotsFired * 100);
        this.single20HitsPercentage = Math.round((double) playerStatistics.getSingle20Hits() / shotsFired * 100);
        this.single25HitsPercentage = Math.round((double) playerStatistics.getSingle25Hits() / shotsFired * 100);
        this.double25HitsPercentage = Math.round((double) playerStatistics.getDouble25Hits() / shotsFired * 100);
        this.doubleHitsPercentage = Math.round((double) playerStatistics.getDoubleHits() / shotsFired * 100);
        this.tripleHitsPercentage = Math.round((double) playerStatistics.getTripleHits() / shotsFired * 100);
    }

    public List<PlayerStatisticsDto> playerStatisticsListToPlayerStatisticsListDto(List<PlayerStatistics> playerStatisticsList) {
        return playerStatisticsList.stream().map(PlayerStatisticsDto::new).collect(Collectors.toList());
    }
}
