package com.example.dart.model;

import com.example.dart.model.enums.ShotType;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.EqualsAndHashCode;

import java.util.Collections;
import java.util.List;

@EqualsAndHashCode
public class PossibleEndingShots implements Comparable<PossibleEndingShots> {

    private final List<Shot> shots;

    public PossibleEndingShots (List<Shot> shots) {
        if (shots.size() > Game.MAX_NUMBER_OF_SHOTS_PER_TURN) {
            throw new IllegalArgumentException("Possible ending can't contain more than " + Game.MAX_NUMBER_OF_SHOTS_PER_TURN + " shots");
        }

        this.shots = shots;
        Collections.sort(this.shots);
    }

    @Override
    @JsonValue
    public String toString() {
        return shots.toString();
    }

    @Override
    public int compareTo(PossibleEndingShots possibleEndingShots) {
        long thisShotsSize = this.shots.size();
        long otherShotsSize = possibleEndingShots.shots.size();

        long thisSinglesAmount = this.shots.stream()
                        .filter(shot -> shot.getShotType() == ShotType.SINGLE)
                        .count();
        long otherSinglesAmount = possibleEndingShots.shots.stream()
                        .filter(shot -> shot.getShotType() == ShotType.SINGLE)
                        .count();

        long thisTriplesAmount = this.shots.stream()
                        .filter(shot -> shot.getShotType() == ShotType.TRIPLE)
                        .count();
        long otherTriplesAmount = possibleEndingShots.shots.stream()
                        .filter(shot -> shot.getShotType() == ShotType.TRIPLE)
                        .count();

        if (thisShotsSize != otherShotsSize) {
            return Long.compare(thisShotsSize, otherShotsSize);
        }

        if (thisSinglesAmount != otherSinglesAmount) {
            return Long.compare(otherSinglesAmount, thisSinglesAmount);
        }

        if (thisTriplesAmount != otherTriplesAmount) {
            return Long.compare(otherTriplesAmount, thisTriplesAmount);
        }

        return possibleEndingShots.toString().compareTo(this.toString());
    }
}
