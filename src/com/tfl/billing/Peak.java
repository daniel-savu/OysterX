package com.tfl.billing;

public class Peak {

    private final float startTime;
    private final float endTime;

    Peak(String startTime, String endTime) {
        this.startTime = Utility.stringTimeToFloatTime(startTime);
        this.endTime = Utility.stringTimeToFloatTime(endTime);
    }

    boolean contains(float journeyTime) {
        return (journeyTime >= startTime && journeyTime <= endTime);
    }

    boolean isContainedInJourney(float journeyStartTime, float journeyEndTime) {
        return (journeyStartTime <= startTime && journeyEndTime >= endTime);
    }
}
