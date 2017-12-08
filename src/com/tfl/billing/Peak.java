package com.tfl.billing;

import java.util.Date;

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

    boolean contains(Date journeyTime) {
        float journeyStartTime = Utility.dateTimeToFloatTime(journeyTime);
        return (journeyStartTime >= startTime && journeyStartTime <= endTime);
    }

    boolean isContainedInJourney(float journeyStartTime, float journeyEndTime) {
        return (journeyStartTime <= startTime && journeyEndTime >= endTime);
    }

    boolean isContainedInJourney(Date start, Date end) {
        float journeyStartTime = Utility.dateTimeToFloatTime(start);
        float journeyEndTime = Utility.dateTimeToFloatTime(end);
        return (journeyStartTime <= startTime && journeyEndTime >= endTime);
    }
}
