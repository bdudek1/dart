package com.example.dart.model;


import com.example.dart.model.enums.GameState;
import com.example.dart.model.enums.ShotType;
import org.junit.jupiter.api.Test;

import static com.example.dart.model.Game.MAX_SCORE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static utils.TestDataHolder.*;

public class GameTest {
    private static final Shot TEST_SHOT = new Shot(20, ShotType.TRIPLE);

    @Test
    public void testGameConstructor() {
        Game testGame = new Game(TEST_PLAYERS);

        assertEquals(TEST_PLAYERS, testGame.getPlayers());
        assertEquals(TEST_PLAYERS.size(), testGame.getPlayerScoresMap().size());
        assertEquals(TEST_PLAYER_1, testGame.getCurrentPlayer());
        assertEquals(0, testGame.getShotsFiredInTurn().size());
        assertEquals(GameState.IN_PROGRESS, testGame.getGameState());
    }

    @Test
    public void performShotTest() {
        Game testGame = new Game(TEST_PLAYERS);
        testGame.performShot(TEST_SHOT);

        assertEquals(1, testGame.getShotsFiredInTurn().size());
        assertEquals(MAX_SCORE - TEST_SHOT.getScore(), testGame.getPlayerScoresMap().get(TEST_PLAYER_1.getName()));
    }

    @Test
    public void performWholeRoundTest() {
        Game testGame = new Game(TEST_PLAYERS);

        assertEquals(TEST_PLAYER_1, testGame.getCurrentPlayer());

        performWholeTurn(testGame);

        assertEquals(MAX_SCORE - TEST_SHOT.getScore() * 3, testGame.getPlayerScoresMap().get(TEST_PLAYER_1.getName()));
        assertEquals(TEST_PLAYER_2, testGame.getCurrentPlayer());

        performWholeTurn(testGame);

        assertEquals(MAX_SCORE - TEST_SHOT.getScore() * 3, testGame.getPlayerScoresMap().get(TEST_PLAYER_2.getName()));
        assertEquals(TEST_PLAYER_3, testGame.getCurrentPlayer());

        performWholeTurn(testGame);

        assertEquals(MAX_SCORE - TEST_SHOT.getScore() * 3, testGame.getPlayerScoresMap().get(TEST_PLAYER_3.getName()));
        assertEquals(TEST_PLAYER_1, testGame.getCurrentPlayer());

    }

    @Test
    public void testFinishGame() {
        Game testGame = new Game(TEST_PLAYERS);

        Shot mockedShot = mock(Shot.class);
        when(mockedShot.getScore()).thenReturn(MAX_SCORE);

        testGame.performShot(mockedShot);

        assertEquals(GameState.FINISHED, testGame.getGameState());
        assertEquals(TEST_PLAYER_1.getName(), testGame.getWinner());
    }

    private void performWholeTurn(Game game) {
        game.performShot(TEST_SHOT);
        game.performShot(TEST_SHOT);
        game.performShot(TEST_SHOT);
    }
}
