package com.example.dart.model;

import com.example.dart.model.enums.GameState;

import lombok.Getter;
import lombok.ToString;
import jakarta.persistence.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

@Entity
@Table(name = "games")
@ToString
@Getter
public class Game {
    public static final int MAX_SCORE = 501;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private GameState gameState;

    @Cascade(CascadeType.REMOVE)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "players_to_games",
            joinColumns = @JoinColumn(name = "game_id", referencedColumnName="id"),
            inverseJoinColumns = @JoinColumn(name = "player_id", referencedColumnName="id"))
    private List<Player> players;

    @ManyToOne
    @JoinColumn(name = "current_player_id")
    private Player currentPlayer;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "game_to_player_scores",
            joinColumns = {@JoinColumn(name = "game_id", referencedColumnName = "id")})
    @MapKeyColumn(name = "player_name")
    @Column(name = "score")
    private final Map<String, Integer> playerScoresMap;

    @Cascade(value={CascadeType.ALL})
    @OnDelete(action= OnDeleteAction.CASCADE)
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "game_to_shots_fired_in_turn",
            joinColumns = {@JoinColumn(name = "game_id", referencedColumnName = "id")})
    @Column(name = "shots_fired_in_turn")
    @JoinColumn
    private Collection<Integer> shotsFiredInTurn;
    private String winner;

    public Game() {
        this.gameState = GameState.IN_PROGRESS;
        this.playerScoresMap = new HashMap<>();
        this.shotsFiredInTurn = new ArrayList<>();
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
        Integer currentScore = playerScoresMap.get(this.currentPlayer.getName());
        Integer shotScore = shot.getScore();
        String currentPlayerName = this.currentPlayer.getName();

        if (shotScore > currentScore) {
            currentScore += shotsFiredInTurn.stream()
                                            .mapToInt(Integer::intValue)
                                            .sum();

            playerScoresMap.put(this.currentPlayer.getName(), currentScore);
            nextTurn();

            return;
        }

        if (Objects.equals(shotScore, currentScore)) {
            finishGame();
        }

        currentScore -= shotScore;
        playerScoresMap.put(currentPlayerName, currentScore);
        shotsFiredInTurn.add(shotScore);

        if (shotsFiredInTurn.size() > 2) {
            nextTurn();
        }
    }

    private void nextTurn() {
        this.shotsFiredInTurn.clear();
        setCurrentPlayer(getNextPlayer(this.currentPlayer));
    }

    private void finishGame() {
        setGameState(GameState.FINISHED);
        setWinner(this.currentPlayer);
    }

    public void setCurrentPlayer(Player player) {
        this.currentPlayer = player;
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

    private Player getFirstPlayer() {
        return this.players.iterator().next();
    }

    public Game getShallowCopy() {
        Game game = new Game();
        game.setPlayers(new LinkedList<>(this.players));
        game.setCurrentPlayer(this.currentPlayer);
        game.setGameState(this.gameState);

        return game;
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
