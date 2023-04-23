package com.example.dart.model;

import com.example.dart.model.enums.ShotType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ShotTest {
    private static final int TEST_SHOT_SCORE = 15;

    @Test
    public void singleShotTest() {
        Shot testShot = new Shot(TEST_SHOT_SCORE, ShotType.SINGLE);

        assertEquals(TEST_SHOT_SCORE, testShot.getScore());
    }

    @Test
    public void doubleShotTest() {
        Shot testShot = new Shot(TEST_SHOT_SCORE, ShotType.DOUBLE);

        assertEquals(TEST_SHOT_SCORE * 2, testShot.getScore());
    }

    @Test
    public void tripleShotTest() {
        Shot testShot = new Shot(TEST_SHOT_SCORE, ShotType.TRIPLE);

        assertEquals(TEST_SHOT_SCORE * 3, testShot.getScore());
    }

    @Test
    public void missedShotTest() {
        Shot testShot = new Shot(TEST_SHOT_SCORE, ShotType.MISS);

        assertEquals(0, testShot.getScore());
    }
}
