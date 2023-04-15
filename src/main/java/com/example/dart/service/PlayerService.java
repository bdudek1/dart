package com.example.dart.service;

import com.example.dart.model.Player;

import java.util.Collection;

public interface PlayerService {
    void save(Player player);
    void createPlayer(String name);
    Collection<Player> findAllPlayers();
    Player findPlayerById(int id);
}
