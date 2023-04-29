package com.example.dart.model.dto;

import com.example.dart.model.Shot;
import com.example.dart.model.enums.ShotType;
import com.example.dart.model.exception.InvalidShotException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShotDto {
    @NotNull(message = "Score is mandatory")
    private Integer score;
    @NotNull(message = "Shot Type is mandatory")
    private ShotType shotType;

    public Shot toShot() {
        if (isShotInvalid()) {
            throw new InvalidShotException();
        }

        return new Shot(score, shotType);
    }

    private boolean isShotInvalid() {
        if (Objects.isNull(score) || Objects.isNull(shotType)) {
            return true;
        }

        if (score >= 0 && score <= 20) {
            return false;
        }

        if (score == 25 && shotType != ShotType.TRIPLE) {
            return false;
        }

        return true;
    }
}
