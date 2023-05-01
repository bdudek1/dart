package com.example.dart.model;

import com.example.dart.model.enums.ShotType;

import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Shot implements Comparable<Shot> {
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

    public ShotType getShotType() {
        return shotType;
    }

    @Override
    @JsonValue
    public String toString() {
        StringBuilder builder = new StringBuilder();

        switch (shotType) {
            case DOUBLE -> builder.append("D");
            case TRIPLE -> builder.append("T");
            case MISS -> {
                builder.append("MISS");
                return builder.toString();
            }
        };

        builder.append(score);

        return builder.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (obj instanceof Shot shot) {
            return shot.getScore().equals(this.getScore()) &&
                   shot.getShotType().equals(this.getShotType());
        }

        return false;
    }

    @Override
    public int compareTo(Shot shot) {
        return shot.getScore().compareTo(this.getScore());
    }
}
