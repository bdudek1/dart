package com.example.dart.repository;

import com.example.dart.model.Player;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Collection;
import java.util.Optional;

public interface PlayerRepository {
    void savePlayer (Player player) throws DataIntegrityViolationException;
    Optional<Player> findPlayerById (int id);
    Collection<Player> findAllPlayers();
}
