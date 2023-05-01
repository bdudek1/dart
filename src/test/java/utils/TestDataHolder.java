package utils;

import com.example.dart.model.Game;
import com.example.dart.model.Player;
import com.example.dart.model.PlayerStatistics;
import com.example.dart.model.RegisteredPlayer;

import java.util.Collection;
import java.util.List;

public class TestDataHolder {
    public static final Player TEST_PLAYER_1 = new Player("testPlayer1");
    public static final Player TEST_PLAYER_2 = new Player("testPlayer2");
    public static final Player TEST_PLAYER_3 = new Player("testPlayer3");
    public static final RegisteredPlayer TEST_REGISTERED_PLAYER_1 = new RegisteredPlayer("testPlayer1", "password".toCharArray());
    public static final RegisteredPlayer TEST_REGISTERED_PLAYER_2 = new RegisteredPlayer("testPlayer2", "password".toCharArray());
    public static final RegisteredPlayer TEST_REGISTERED_PLAYER_3 = new RegisteredPlayer("testPlayer3", "password".toCharArray());
    public static final Collection<Player> TEST_PLAYERS = List.of(TEST_PLAYER_1, TEST_PLAYER_2, TEST_PLAYER_3);
    public static final Collection<Player> TEST_REGISTERED_PLAYERS = List.of(TEST_REGISTERED_PLAYER_1, TEST_REGISTERED_PLAYER_2, TEST_REGISTERED_PLAYER_3);
    public static final Game TEST_GAME = new Game(TEST_PLAYERS);
    public static final PlayerStatistics TEST_PLAYER_STATISTICS_1 = new PlayerStatistics(TEST_REGISTERED_PLAYER_1);
    public static final PlayerStatistics TEST_PLAYER_STATISTICS_2 = new PlayerStatistics(TEST_REGISTERED_PLAYER_2);
    public static final PlayerStatistics TEST_PLAYER_STATISTICS_3 = new PlayerStatistics(TEST_REGISTERED_PLAYER_3);
    public static final List<PlayerStatistics> TEST_PLAYER_STATISTICS = List.of(TEST_PLAYER_STATISTICS_1, TEST_PLAYER_STATISTICS_2, TEST_PLAYER_STATISTICS_3);
}
