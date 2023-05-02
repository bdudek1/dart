package com.example.dart.model;

import com.example.dart.model.enums.GameState;

import lombok.Getter;
import lombok.ToString;

import jakarta.persistence.*;
import jakarta.persistence.Table;

import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import java.util.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

@Entity
@Table(name = "games")
@ToString
@Getter
public class Game {
    public static final int MAX_NUMBER_OF_SHOTS_PER_TURN = 3;
    public static final int MAX_REACHABLE_SHOT_SCORE = 60;
    public static final int MAX_SCORE = 501;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private GameState gameState;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "players_to_games",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName="id"))
    @Cascade(CascadeType.REMOVE)
    @Fetch(FetchMode.SUBSELECT)
    private List<Player> players;

    @ManyToOne
    @JoinColumn(name = "current_player_id")
    @Cascade(CascadeType.REMOVE)
    private Player currentPlayer;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "game_to_player_scores",
            joinColumns = {@JoinColumn(name = "game_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "player_name")
    @Column(name = "score")
    private final Map<String, Integer> playerScoresMap;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "game_to_shots_fired_in_turn")
    @Column(name = "shots_fired_in_turn")
    @JoinColumn
    @Fetch(FetchMode.SUBSELECT)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Collection<Integer> shotsFiredInTurn;
    private String winner;
    @Transient
    private final Set<PossibleEndingShots> possibleEndingShots;

    public Game() {
        this.gameState = GameState.IN_PROGRESS;
        this.playerScoresMap = new HashMap<>();
        this.shotsFiredInTurn = new ArrayList<>();
        this.possibleEndingShots = new TreeSet<>();
    }

    public Game(Collection<Player> players) {
        this();
        this.players = new LinkedList<>(players);

        for (Player player : players) {
            playerScoresMap.put(player.getName(), MAX_SCORE);
        }

        setCurrentPlayer(getFirstPlayer());
    }

    public void performShot(Shot shot) {
        String currentPlayerName = this.currentPlayer.getName();
        Integer currentScore = playerScoresMap.get(currentPlayerName);
        Integer shotScore = shot.getScore();

        if (shotScore > currentScore) {
            currentScore += shotsFiredInTurn.stream()
                                            .mapToInt(Integer::intValue)
                                            .sum();

            playerScoresMap.put(currentPlayerName, currentScore);
            nextTurn();

            return;
        }

        if (Objects.equals(shotScore, currentScore)) {
            finishGame();
        }

        currentScore -= shotScore;
        playerScoresMap.put(currentPlayerName, currentScore);
        shotsFiredInTurn.add(shotScore);

        if (shotsFiredInTurn.size() >= MAX_NUMBER_OF_SHOTS_PER_TURN) {
            nextTurn();
        } else {
            calculatePossibleEndingShots();
        }
    }

    private void nextTurn() {
        this.shotsFiredInTurn.clear();
        setCurrentPlayer(getNextPlayer(this.currentPlayer));
        calculatePossibleEndingShots();
    }

    private void finishGame() {
        setGameState(GameState.FINISHED);
        setWinner(this.currentPlayer);
    }

    private Player getNextPlayer(Player player) {
        Iterator<Player> playerIterator = players.iterator();

        while (playerIterator.hasNext()) {
            Player nextPlayer = playerIterator.next();

            if (nextPlayer.equals(player)) {
                if (playerIterator.hasNext()) {
                    return playerIterator.next();
                } else {
                    return getFirstPlayer();
                }
            }
        }

        return getFirstPlayer();
    }

    private void calculatePossibleEndingShots() {
        this.possibleEndingShots.clear();

        String currentPlayerName = this.currentPlayer.getName();
        Integer currentScore = playerScoresMap.get(currentPlayerName);
        Integer shotsLeft = MAX_NUMBER_OF_SHOTS_PER_TURN - shotsFiredInTurn.size();

        switch (shotsLeft) {
            case 3 -> calculateThreePossibleEndingShots(currentScore);
            case 2 -> calculateTwoPossibleEndingShots(currentScore);
            case 1 -> calculateOnePossibleEndingShot(currentScore);
        }
    }

    private void calculateThreePossibleEndingShots(Integer currentScore) {
        if (currentScore > MAX_REACHABLE_SHOT_SCORE * 3) {
            return;
        }

        for (Shot possibleFirstShot: ValidShots.VALID_SHOTS) {
            for (Shot possibleSecondShot: ValidShots.VALID_SHOTS) {
                for (Shot possibleThirdShot: ValidShots.VALID_SHOTS) {
                    Integer possibleScore = possibleFirstShot.getScore() +
                                            possibleSecondShot.getScore() +
                                            possibleThirdShot.getScore();

                    if (possibleScore.equals(currentScore)) {
                        this.possibleEndingShots.add(new PossibleEndingShots(
                                Arrays.asList(possibleFirstShot, possibleSecondShot, possibleThirdShot)
                        ));
                    }
                }
            }
        }

        calculateTwoPossibleEndingShots(currentScore);
        calculateOnePossibleEndingShot(currentScore);
    }

    private void calculateTwoPossibleEndingShots(Integer currentScore) {
        if (currentScore > MAX_REACHABLE_SHOT_SCORE * 2) {
            return;
        }

        for (Shot possibleFirstShot: ValidShots.VALID_SHOTS) {
            for (Shot possibleSecondShot: ValidShots.VALID_SHOTS) {
                Integer possibleScore = possibleFirstShot.getScore() + possibleSecondShot.getScore();

                if (possibleScore.equals(currentScore)) {
                    this.possibleEndingShots.add(new PossibleEndingShots(Arrays.asList(possibleFirstShot, possibleSecondShot)));
                }
            }
        }

        calculateOnePossibleEndingShot(currentScore);
    }

    private void calculateOnePossibleEndingShot(Integer currentScore) {
        if (currentScore > MAX_REACHABLE_SHOT_SCORE) {
            return;
        }

        for (Shot possibleShot: ValidShots.VALID_SHOTS) {
            if (currentScore.equals(possibleShot.getScore())) {
                this.possibleEndingShots.add(new PossibleEndingShots(Arrays.asList(possibleShot)));
            }
        }
    }

    public Game getShallowCopyWithCurrentPlayer(Player currentPlayer) {
        Game game = new Game();
        game.setPlayers(new LinkedList<>(this.players));
        game.setCurrentPlayer(currentPlayer);
        game.setGameState(this.gameState);

        if (game.getGameState() == GameState.FINISHED) {
            game.setWinner(currentPlayer);
        }

        return game;
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
    }

    public void clearShotsFiredInTurn() {
        this.shotsFiredInTurn.clear();
    }

    private Player getFirstPlayer() {
        return this.players.iterator().next();
    }

    private void setPlayers(List<Player> players) {
        this.players = players;
    }

    private void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private void setWinner(Player player) {
        this.winner = player.getName();
    }
}
