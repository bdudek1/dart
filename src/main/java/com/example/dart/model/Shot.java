package com.example.dart.model;

import com.example.dart.model.enums.ShotType;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class Shot {
    private Integer score;
    private ShotType shotType;

    public Integer getScore() {
        return switch (shotType) {
            case SINGLE -> score;
            case DOUBLE -> score * 2;
            case TRIPLE -> score * 3;
            case MISS -> 0;
        };
    }
}
