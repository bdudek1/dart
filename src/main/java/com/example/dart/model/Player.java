package com.example.dart.model;

import com.example.dart.model.dto.PlayerDto;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "players")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="guest")
@Data
public class Player {
    @Column(unique = true, nullable=false)
    private final String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    public Player(String name) {
        this.name = name;
    }
    public Player(PlayerDto playerDto) {
        this.name = playerDto.getName();
    }
    public Player() { this.name = null; }
}
