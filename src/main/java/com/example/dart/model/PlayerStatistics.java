package com.example.dart.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "player_statistics")
@NoArgsConstructor
@Data
public class PlayerStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @OneToOne
    @JoinColumn(name = "player_id")
    private RegisteredPlayer player;
    @Column(name = "games_played")
    private float gamesPlayed;
    @Column(name = "games_won")
    private float gamesWon;
    @Column(name = "shots_fired")
    private float shotsFired;
    @Column(name = "triple_20_hits")
    private float triple20Hits;
    @Column(name = "double_20_hits")
    private float double20Hits;
    @Column(name = "single_20_hits")
    private float single20Hits;
    @Column(name = "single_25_hits")
    private float single25Hits;
    @Column(name = "double_25_hits")
    private float double25Hits;
    @Column(name="double_hits")
    private float doubleHits;
    @Column(name="triple_hits")
    private float tripleHits;

    public PlayerStatistics(RegisteredPlayer player) {
        this.player = player;

        //we need those fields to be 1 at start to prevent dividing by 0 while querying db
        incrementGamesPlayed();
        incrementShotsFired();
    }

    public void incrementGamesPlayed() {
        this.gamesPlayed++;
    }

    public void incrementGamesWon() {
        this.gamesWon++;
    }

    public void incrementShotsFired() {
        this.shotsFired++;
    }

    public void incrementTriple20Hits() {
        this.triple20Hits++;
    }

    public void incrementDouble20Hits() {
        this.double20Hits++;
    }

    public void incrementSingle20Hits() {
        this.single20Hits++;
    }

    public void incrementSingle25Hits() {
        this.single25Hits++;
    }

    public void incrementDouble25Hits() {
        this.double25Hits++;
    }

    public void incrementDoubleHits() {
        this.doubleHits++;
    }

    public void incrementTripleHits() {
        this.tripleHits++;
    }
}
