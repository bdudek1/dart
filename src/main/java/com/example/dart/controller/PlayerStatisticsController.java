package com.example.dart.controller;

import com.example.dart.model.dto.PlayerStatisticsDto;
import com.example.dart.model.enums.PlayerStatisticsOrderType;
import com.example.dart.service.PlayerStatisticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class PlayerStatisticsController {

    private PlayerStatisticsService playerStatisticsService;

    @Autowired
    public PlayerStatisticsController(PlayerStatisticsService playerStatisticsService) {
        this.playerStatisticsService = playerStatisticsService;
    }

    @GetMapping("/player-statistics")
    public List<PlayerStatisticsDto> getPlayerStatistics(@RequestParam(name = "orderType") PlayerStatisticsOrderType orderType,
                                                         @RequestParam(name = "page") int page) {
        return playerStatisticsService.getOrderedPlayerStatistics(orderType, page);
    }
}
