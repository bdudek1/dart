package com.example.dart.service;

import com.example.dart.model.Game;
import com.example.dart.model.Player;
import com.example.dart.model.Shot;
import com.example.dart.model.dto.GameDto;

import java.util.Collection;

public interface GameService {
    Game createGame(Collection<Player> players);
    Game findGameById(Long id);
    Collection<Game> findGamesByPlayerId(Long id);
    GameDto addShotAndReturnGameDto(Long gameId, Shot shot);
}
