package com.example.dart.model;

import jakarta.persistence.*;
@Entity
@DiscriminatorValue("registered")
public class RegisteredPlayer extends Player {
    public RegisteredPlayer(String name) {
        super(name);
    }
}
