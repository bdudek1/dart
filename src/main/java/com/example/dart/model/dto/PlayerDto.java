package com.example.dart.model.dto;

import com.example.dart.model.Player;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PlayerDto {
    @NotBlank(message = "Name is mandatory")
    private String name;

    public Player toPlayer() {
        return new Player(this.name);
    }
}
