package com.example.dart.model.dto;

import com.example.dart.model.Game;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static utils.TestDataHolder.TEST_PLAYERS;

public class GameDtoTest {
    private static final Game TEST_GAME = new Game(TEST_PLAYERS);

    @Test
    public void gameDtoConstructorTest() {
        GameDto gameDto = new GameDto(TEST_GAME);

        assertEquals(TEST_GAME.getId(), gameDto.getId());
        assertEquals(TEST_GAME.getCurrentPlayer().getName(), gameDto.getCurrentPlayer());
        assertEquals(TEST_GAME.getWinner(), gameDto.getWinner());
        assertEquals(TEST_GAME.getPlayerScoresMap().size(), gameDto.getPlayerScoresMap().size());
        assertEquals(TEST_GAME.getGameState(), gameDto.getGameState());
    }
}
