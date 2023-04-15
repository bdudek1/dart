package com.example.dart.service.impl;

import com.example.dart.model.Player;
import com.example.dart.repository.PlayerRepository;
import com.example.dart.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
public class PlayerServiceImpl implements PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public void save(Player player) {
        playerRepository.savePlayer(player);
    }

    public void createPlayer(String name) {
        Player player = new Player(name);
        playerRepository.savePlayer(player);
    }

    public Collection<Player> findAllPlayers() {
        return Collections.unmodifiableCollection(playerRepository.findAllPlayers());
    }

    public Player findPlayerById(int id) {
        return playerRepository.findPlayerById(id);
    }
}
