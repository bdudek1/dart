package com.example.dart.model.dto;

import com.example.dart.model.Player;
import com.example.dart.model.RegisteredPlayer;
import com.example.dart.model.enums.PlayerType;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDto {
    @NotBlank(message = "Name is mandatory")
    private String name;
    private Long id;
    private char[] password;
    private PlayerType playerType;

    public PlayerDto(Player player) {
        this.name = player.getName();
        this.id = player.getId();
        this.playerType = player.getPlayerType();
    }

    public PlayerDto(String name) {
        this.name = name;
    }
    public PlayerDto(String name, char[] password) {
        this.name = name;
        this.password = password;
    }

    public Player toPlayer() {
        if(Objects.isNull(this.password)) {
            return new Player(this.name);
        } else {
            return new RegisteredPlayer(this.name, this.password);
        }
    }

    @Override
    public String toString() {
        return this.name;
    }

    public boolean isGuest() {
        return this.playerType == PlayerType.GUEST;
    }
}
