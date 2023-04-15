package com.example.dart.model;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "players")
@Getter
public class Player {
    String name;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    public Player(String name) {
        this.name = name;
    }
}
