package com.example.dart.model.dto;

import com.example.dart.model.Shot;
import com.example.dart.model.enums.ShotType;
import com.example.dart.model.exception.InvalidShotException;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ShotDto {
    @NotNull(message = "Score is mandatory")
    private Integer score;
    @NotNull(message = "Shot Type is mandatory")
    private ShotType shotType;

    public Shot toShot() {
        if (score < 0 || score > 20) {
            throw new InvalidShotException();
        }

        return new Shot(score, shotType);
    }
}
