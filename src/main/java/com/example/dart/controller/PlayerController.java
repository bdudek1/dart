package com.example.dart.controller;

import com.example.dart.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerController {
    @Autowired
    PlayerService playerService;

    @GetMapping("/players")
    public String getPlayers() {
        return playerService.findAllPlayers().toString();
    }

    @GetMapping("/player/{id}")
    public String getPlayerById(int id) {
        return playerService.findPlayerById(id).toString();
    }

    @PostMapping("/player")
    public void createPlayer(@RequestParam(name = "name") String name) {
        playerService.createPlayer(name);
    }
}
