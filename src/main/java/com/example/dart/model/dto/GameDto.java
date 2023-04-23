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
    private LinkedHashMap<PlayerDto, Integer> playerScoresMap;
    private String currentPlayer;
    private Long id;
    private String winner;
    private GameState gameState;

    public GameDto(Game game) {
        setPlayerScoresMap(game);

        this.currentPlayer = game.getCurrentPlayer().getName();

        this.gameState = game.getGameState();
        this.winner = game.getWinner();
        this.id = game.getId();
    }

    public boolean isFinished() {
        return this.gameState == GameState.FINISHED;
    }

    private void setPlayerScoresMap(Game game) {
        this.playerScoresMap = new LinkedHashMap<>();

        game.getPlayerScoresMap().forEach((key, value) -> {
            this.playerScoresMap.put(new PlayerDto(key), value);
        });
    }
}
