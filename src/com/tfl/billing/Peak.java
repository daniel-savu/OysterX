package com.tfl.billing;

public class Peak {

    private final float startTime;
    private final float endTime;

    public Peak(String startTime, String endTime) {
        this.startTime = Utility.stringTimeToFloatTime(startTime);
        this.endTime = Utility.stringTimeToFloatTime(endTime);
    }

    public boolean contains(float journeyTime) {
        if (journeyTime >= startTime && journeyTime <= endTime) {
            return true;
        }
        return false;
    }

    public boolean isContainedInJourney(float journeyStartTime, float journeyEndTime) {
        if (journeyStartTime <= startTime && journeyEndTime >= endTime) {
            return true;
        }
        return false;
    }
}
