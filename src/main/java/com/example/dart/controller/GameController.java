package com.example.dart.controller;

import com.example.dart.model.Game;
import com.example.dart.model.Player;
import com.example.dart.model.dto.GameDto;
import com.example.dart.model.dto.PlayerDto;
import com.example.dart.model.dto.ShotDto;
import com.example.dart.service.GameService;
import com.example.dart.service.PlayerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collection;

@Validated
@RestController
public class GameController {
    private GameService gameService;
    private PlayerService playerService;

    @Autowired
    public GameController(GameService gameService, PlayerService playerService) {
        this.gameService = gameService;
        this.playerService = playerService;
    }

    @PostMapping("/game")
    public ResponseEntity<GameDto> createGame(@Valid @RequestBody Collection<PlayerDto> playerDtos) {
        Collection<Player> players = new ArrayList<>();

        playerDtos.forEach(playerDto -> {
            players.add(playerService.findPlayerByName(playerDto.getName()));
        });

        Game createdGame = gameService.createGame(players);

        return ResponseEntity.ok(new GameDto(createdGame));
    }

    @GetMapping("/game/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable(name = "id") Long id) {
        Game game = gameService.findGameById(id);

        return ResponseEntity.ok(new GameDto(game));
    }

    @GetMapping("/games")
    public ResponseEntity<Collection<GameDto>> getAllGamesByPlayerId(@RequestParam(name = "playerId") Long playerId) {
        Collection<Game> games = gameService.findGamesByPlayerId(playerId);

        return ResponseEntity.ok(new ArrayList<>(games.stream()
                                                      .map(GameDto::new)
                                                      .toList()));
    }

    @PostMapping("/game/{id}/shot")
    public ResponseEntity<GameDto> shot(@PathVariable(name = "id") Long id, @Valid @RequestBody ShotDto shotDto) {
        GameDto gameDto = gameService.addShotAndReturnGameDto(id, shotDto.toShot());

        return ResponseEntity.ok(gameDto);
    }

}
