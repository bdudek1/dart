package com.example.dart.model;

import com.example.dart.model.enums.PlayerType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("registered")
public class RegisteredPlayer extends Player {
    char[] password;
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
}
