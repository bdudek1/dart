package com.example.dart.service.impl;

import com.example.dart.model.Player;
import com.example.dart.model.exception.EntityAlreadyExistsException;
import com.example.dart.model.exception.EntityNotFoundException;
import com.example.dart.repository.PlayerRepository;
import com.example.dart.service.PlayerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PlayerServiceImpl implements PlayerService {
    private static final Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);
    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createPlayer(Player player) {
        try {
            playerRepository.savePlayer(player);
        } catch (DataIntegrityViolationException e) {
            logger.warn("Could not save player because player with name {} already exists.", player.getName());
            throw new EntityAlreadyExistsException(e.getMessage());
        }

        return player;
    }

    public Collection<Player> findAllPlayers() {
        return playerRepository.findAllPlayers();
    }

    public Player findPlayerById(int id) {
        return playerRepository.findPlayerById(id)
                               .orElseThrow(EntityNotFoundException::new);
    }
}
