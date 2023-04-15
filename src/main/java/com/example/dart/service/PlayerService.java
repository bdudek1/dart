package com.example.dart.service;

import com.example.dart.model.Player;
import com.example.dart.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    public void save(Player player) {
        playerRepository.savePlayer(player);
    }

    public void createPlayer(String name) {
        Player player = new Player(name);
        playerRepository.savePlayer(player);
    }

    public List<Player> findAllPlayers() {
        return playerRepository.findAllPlayers();
    }

    public Player findPlayerById(int id) {
        return playerRepository.findPlayerById(id);
    }
}
