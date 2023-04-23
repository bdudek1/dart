package utils;

import com.example.dart.model.Game;
import com.example.dart.model.Player;

import java.util.Collection;
import java.util.List;

public class TestDataHolder {
    public static final Player TEST_PLAYER_1 = new Player("testPlayer1");
    public static final Player TEST_PLAYER_2 = new Player("testPlayer2");
    public static final Player TEST_PLAYER_3 = new Player("testPlayer3");
    public static final Collection<Player> TEST_PLAYERS = List.of(TEST_PLAYER_1, TEST_PLAYER_2, TEST_PLAYER_3);
    public static final Game TEST_GAME = new Game(TEST_PLAYERS);
}
