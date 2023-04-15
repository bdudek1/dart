package com.example.dart.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "players")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name="type", discriminatorType=DiscriminatorType.STRING)
@DiscriminatorValue(value="guest")
@Getter
public class Player {
    private final String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    public Player(String name) {
        this.name = name;
    }
}
