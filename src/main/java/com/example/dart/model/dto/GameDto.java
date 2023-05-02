package com.example.dart.model.dto;

import com.example.dart.model.Game;
import com.example.dart.model.PossibleEndingShots;
import com.example.dart.model.enums.GameState;

import lombok.Getter;
import lombok.ToString;

import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Set;

@ToString
@Getter
public class GameDto {
    private LinkedHashMap<PlayerDto, Integer> playerScoresMap;
    private String currentPlayer;
    private final Long id;
    private final String winner;
    private final GameState gameState;
    private final Set<PossibleEndingShots> possibleEndingShots;

    public GameDto(Game game) {
        setPlayerScoresMap(game);

        if (Objects.nonNull(game.getCurrentPlayer())) {
            this.currentPlayer = game.getCurrentPlayer().getName();
        }

        this.gameState = game.getGameState();
        this.winner = game.getWinner();
        this.id = game.getId();
        this.possibleEndingShots = game.getPossibleEndingShots();
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
