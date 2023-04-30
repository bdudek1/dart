package com.example.dart.service.impl;

import com.example.dart.model.Game;
import com.example.dart.model.Player;
import com.example.dart.model.Shot;
import com.example.dart.model.dto.GameDto;
import com.example.dart.model.enums.GameState;
import com.example.dart.model.enums.PlayerType;
import com.example.dart.model.exception.EntityNotFoundException;
import com.example.dart.model.exception.GameNotInProgressException;
import com.example.dart.model.exception.NotEnoughPlayersException;
import com.example.dart.repository.GameRepository;
import com.example.dart.service.GameService;
import com.example.dart.service.PlayerStatisticsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class GameServiceImpl implements GameService {
    private static final Logger logger = LoggerFactory.getLogger(GameServiceImpl.class);
    private final GameRepository gameRepository;
    private final PlayerStatisticsService playerStatisticsService;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository,  PlayerStatisticsService playerStatisticsService) {
        this.gameRepository = gameRepository;
        this.playerStatisticsService = playerStatisticsService;
    }

    public Game createGame(Collection<Player> players) {
        if (players.size() < 2) {
            logger.warn("Not enough players. Can't create a game. At least 2 players are required.");
            throw new NotEnoughPlayersException();
        }

        Game game = new Game(players);
        gameRepository.saveGame(game);

        return game;
    }

    public Game findGameById(Long id) {
        return gameRepository.findGameById(id)
                             .orElseThrow(EntityNotFoundException::new);
    }

    public Collection<Game> findGamesByPlayerId(Long playerId) {
        return gameRepository.findGamesByPlayerId(playerId);
    }

    public GameDto addShotAndReturnGameDto(Long gameId, Shot shot) {
        Game game = findGameById(gameId);
        Player currentPlayer = game.getCurrentPlayer();

        if (game.getGameState() != GameState.IN_PROGRESS) {
            logger.warn("Game {} is not in progress. Can't perform a shot.", gameId);
            throw new GameNotInProgressException();
        }

        game.performShot(shot);

        Game gameMemo = game.getShallowCopyWithCurrentPlayer(currentPlayer);

        if (game.getGameState() == GameState.FINISHED) {
            finalizeGame(game);
        }

        gameRepository.updateGame(game);

        playerStatisticsService.updatePlayerStatistics(gameMemo, shot);

        logger.info("Player {} made a shot {}", gameMemo.getCurrentPlayer().getName(), shot);

        return new GameDto(game);
    }

    private void removeUnregisteredPlayers(Game game) {
        game.getPlayers().removeIf(player -> player.getPlayerType().equals(PlayerType.GUEST));
    }

    private void finalizeGame(Game game) {
        removeUnregisteredPlayers(game);
        game.setCurrentPlayer(null);
        game.clearShotsFiredInTurn();
    }
}
