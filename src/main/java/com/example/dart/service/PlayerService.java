package com.example.dart.service;

import com.example.dart.model.Player;

import java.util.Collection;

public interface PlayerService {
    void deletePlayer(Player player);
    boolean arePlayerCredentialsValid(String name, String password);
    Player createPlayer(Player player);
    Collection<Player> findAllPlayers();
    Player findPlayerById(int id);
    Player findPlayerByName(String name);
}
