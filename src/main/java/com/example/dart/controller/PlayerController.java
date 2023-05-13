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
import java.util.Objects;

@Validated
@RestController
public class PlayerController {

    private PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/player/{id}")
    public ResponseEntity<PlayerDto> getPlayerById(@PathVariable(name = "id") int id) {
        return ResponseEntity.ok(new PlayerDto(playerService.findPlayerById(id)));
    }

    @GetMapping("/player")
    public ResponseEntity<PlayerDto> getPlayerByName(@RequestParam(name = "name") String name) {
        return ResponseEntity.ok(new PlayerDto(playerService.findPlayerByName(name)));
    }

    @GetMapping("/players")
    public ResponseEntity<Collection<PlayerDto>> getPlayers() {
        Collection<Player> players = playerService.findAllPlayers();

        return ResponseEntity.ok(new PlayerDto().playerCollectionToPlayerDtoCollection(players));
    }

    @PostMapping("/player")
    public ResponseEntity<PlayerDto> createPlayer(@Valid @RequestBody PlayerDto playerDto) {
        Player player = playerService.createPlayer(playerDto.toPlayer());

        return new ResponseEntity<>(new PlayerDto(player), HttpStatus.CREATED);
    }

    @GetMapping("/player/validate-credentials")
    public boolean arePlayerCredentialsValid(@RequestParam(name = "name") String name,
                                             @RequestParam(name = "password") String password) {
        if (Objects.isNull(name) || Objects.isNull(password)) {
            return false;
        }

        return playerService.arePlayerCredentialsValid(name, password);
    }
}
