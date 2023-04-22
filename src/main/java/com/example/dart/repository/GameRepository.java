package com.example.dart.repository;

import com.example.dart.model.Game;

import java.util.Collection;
import java.util.Optional;

public interface GameRepository {
    void saveGame(Game game);
    void updateGame(Game game);
    void deleteGame(Game game);
    Collection<Game> findGamesByPlayerId(Long playerId);
    Optional<Game> findGameById(Long id);
}
