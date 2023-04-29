package com.example.dart.model;

import com.example.dart.model.enums.PlayerType;
import jakarta.persistence.*;

@Entity
@DiscriminatorValue("registered")
public class RegisteredPlayer extends Player {
    private char[] password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "player_statistics_id")
    private PlayerStatistics playerStatistics;

    public RegisteredPlayer(String name, char[] password) {
        super(name);
        this.password = password;
    }

    public RegisteredPlayer() {
        super();
    }

    public PlayerType getPlayerType() {
        return PlayerType.REGISTERED;
    }

    public PlayerStatistics getPlayerStatistics() {
        return playerStatistics;
    }

    public void setPlayerStatistics(PlayerStatistics playerStatistics) {
        this.playerStatistics = playerStatistics;
    }
}
