package com.example.dart.service;

import com.example.dart.model.Game;
import com.example.dart.model.Shot;
import com.example.dart.model.dto.PlayerStatisticsDto;
import com.example.dart.model.enums.PlayerStatisticsOrderType;

import java.util.List;

public interface PlayerStatisticsService {
    List<PlayerStatisticsDto> getOrderedPlayerStatistics(PlayerStatisticsOrderType orderType, int page);
    void updatePlayerStatistics(Game game, Shot shot);
}
