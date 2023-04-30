package com.example.dart.model.dto;

import com.example.dart.model.Game;
import com.example.dart.model.enums.GameState;

import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Objects;

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

        if (Objects.nonNull(game.getCurrentPlayer())) {
            this.currentPlayer = game.getCurrentPlayer().getName();
        }

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
