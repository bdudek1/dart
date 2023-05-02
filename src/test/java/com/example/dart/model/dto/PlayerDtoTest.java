package com.example.dart.model.dto;

import com.example.dart.model.Player;
import com.example.dart.model.RegisteredPlayer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static utils.TestDataHolder.TEST_PLAYERS;

public class PlayerDtoTest {
    private static final String TEST_NAME = "testName";
    private static final char[] TEST_PASSWORD = {'a', 'b', 'c', 'd', 'e'};

    @Test
    public void playerDtoToPlayerShouldReturnPlayer() {
        PlayerDto playerDto = new PlayerDto(TEST_NAME);
        Player player = playerDto.toPlayer();

        assertFalse(player instanceof RegisteredPlayer);

        PlayerDto playerDtoFromPlayer = new PlayerDto(player);
        assertTrue(playerDtoFromPlayer.isGuest());
    }

    @Test
    public void playerDtoToPlayerShouldReturnRegisteredPlayer() {
        PlayerDto playerDto = new PlayerDto(TEST_NAME, TEST_PASSWORD);
        Player player = playerDto.toPlayer();

        assertTrue(player instanceof RegisteredPlayer);

        PlayerDto playerDtoFromPlayer = new PlayerDto(player);
        assertFalse(playerDtoFromPlayer.isGuest());
    }

    @Test
    public void playerCollectionToPlayerDtoCollectionTest() {
        assertEquals(TEST_PLAYERS.size(), new PlayerDto().playerCollectionToPlayerDtoCollection(TEST_PLAYERS).size());
    }
}
