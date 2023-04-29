package com.example.dart.model.dto;

import com.example.dart.model.Shot;
import com.example.dart.model.enums.ShotType;
import com.example.dart.model.exception.InvalidShotException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ShotDtoTest {
    private static final int TEST_SHOT_SCORE = 15;
    private static final int TEST_SHOT_SCORE_BULLSEYE = 25;
    private static final int TEST_SHOT_SCORE_MORE_THAN_20 = 30;
    private static final int TEST_SHOT_SCORE_LESS_THAN_0 = -5;

    @Test
    public void testToShot() {
        ShotDto shotDto = new ShotDto(TEST_SHOT_SCORE, ShotType.DOUBLE);
        Shot shot = shotDto.toShot();

        assertEquals(TEST_SHOT_SCORE * 2, shot.getScore());
    }

    @Test
    public void testToShotScoreMoreThan20() {
        ShotDto shotDto = new ShotDto(TEST_SHOT_SCORE_MORE_THAN_20, ShotType.DOUBLE);

        assertThrows(InvalidShotException.class, shotDto::toShot);
    }

    @Test
    public void testToShotScoreLessThan0() {
        ShotDto shotDto = new ShotDto(TEST_SHOT_SCORE_LESS_THAN_0, ShotType.DOUBLE);

        assertThrows(InvalidShotException.class, shotDto::toShot);
    }

    @Test
    public void testToShotScoreEquals25() {
        ShotDto shotDto = new ShotDto(TEST_SHOT_SCORE_BULLSEYE, ShotType.DOUBLE);
        Shot shot = shotDto.toShot();

        assertEquals(TEST_SHOT_SCORE_BULLSEYE * 2, shot.getScore());
    }

    @Test
    public void testToShotTriple25() {
        ShotDto shotDto = new ShotDto(TEST_SHOT_SCORE_BULLSEYE, ShotType.TRIPLE);

        assertThrows(InvalidShotException.class, shotDto::toShot);
    }

    @Test
    public void testToShotWithNullScore() {
        ShotDto shotDto = new ShotDto(null, ShotType.DOUBLE);

        assertThrows(InvalidShotException.class, shotDto::toShot);
    }

    @Test
    public void testToShotWithNullShotType() {
        ShotDto shotDto = new ShotDto(TEST_SHOT_SCORE, null);

        assertThrows(InvalidShotException.class, shotDto::toShot);
    }
}
