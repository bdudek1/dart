package com.example.dart.repository;

import com.example.dart.model.Player;

import java.util.List;

public interface PlayerRepository {
    void savePlayer (Player player);
    Player findPlayerById (int id);
    List<Player> findAllPlayers();
}
