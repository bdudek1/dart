package com.example.dart.model.dto;

import com.example.dart.model.Game;
import com.example.dart.model.Player;
import com.example.dart.model.enums.GameState;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.ToString;

import java.util.Collection;
import java.util.LinkedHashMap;

@ToString
@Getter
public class GameDto {
    private final LinkedHashMap<PlayerDto, Integer> playerScoresMap;
    private String currentPlayer;
    private Long id;
    private String winner;
    private GameState gameState;

    public GameDto(Game game) {
        this.playerScoresMap = new LinkedHashMap<>();
        setPlayerScoresMap(game);

        this.currentPlayer = game.getCurrentPlayer().getName();

        this.gameState = game.getGameState();
        this.winner = game.getWinner();
        this.id = game.getId();
    }

    public boolean isFinished() {
        return this.gameState == GameState.FINISHED;
    }

    @JsonIgnore
    public Collection<PlayerDto> getGuests() {
        return this.playerScoresMap.keySet()
                                   .stream()
                                   .filter(PlayerDto::isGuest)
                                   .toList();
    }
    
    private Player getPlayerFromGameByName(Game game, String name) {
        return game.getPlayers()
                .stream()
                .filter(player -> player.getName().equals(name))
                .findFirst()
                .get();
    }

    private void setPlayerScoresMap(Game game) {
        game.getPlayerScoresMap().forEach((key, value) -> {
            this.playerScoresMap.put(new PlayerDto(getPlayerFromGameByName(game, key)), value);
        });
    }
}
