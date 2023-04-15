package com.example.dart.controller;

import com.example.dart.model.Player;
import com.example.dart.model.dto.PlayerDto;
import com.example.dart.service.PlayerService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Validated
@RestController
public class PlayerController {
    @Autowired
    PlayerService playerService;

    @GetMapping("/players")
    public ResponseEntity<Collection<Player>> getPlayers() {
        return ResponseEntity.ok(playerService.findAllPlayers());
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(playerService.findPlayerById(id));
    }

    @PostMapping("/player")
    public ResponseEntity<Player> createPlayer(@Valid @RequestBody PlayerDto playerDto) {
        Player player = playerService.createPlayer(playerDto.toPlayer());

        return new ResponseEntity<>(player, HttpStatus.CREATED);
    }
}
