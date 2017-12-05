package com.tfl.billing;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class Calculator {
    private static final BigDecimal PEAK_LONG_JOURNEY_PRICE = new BigDecimal(3.80);
    private static final BigDecimal PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(2.90);
    private static final BigDecimal OFF_PEAK_LONG_JOURNEY_PRICE = new BigDecimal(2.70);
    private static final BigDecimal OFF_PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(1.60);
    private static final int LONG_JOURNEY_DURATION_IN_MINUTES = 25;
    private static final int SECONDS_IN_A_MINUTE = 60;
    private static final int MORNING_PEAK_START = 6;
    private static final int MORNING_PEAK_END = 10;
    private static final int EVENING_PEAK_START = 17;
    private static final int EVENING_PEAK_END = 20;

    public boolean journeyIsPeakTime(Journey journey) {

        return isPeak(journey.startTime()) || isPeak(journey.endTime()) || isPeak(journey.startTime(), journey.endTime());
    }

    public boolean isPeak(Date time) {
        int hour = getCurrentHour(time);
        if (isMorningPeak(hour)) {
            return true;
        }
        if (isEveningPeak(hour)) {
            return true;
        }
        return false;
    }

    public boolean isPeak(Date timeStart, Date timeEnd) {
        int hourStart = getCurrentHour(timeStart);
        int hourEnd = getCurrentHour(timeEnd);

        if (containsMorningPeak(hourStart, hourEnd)) {
            return true;
        }
        if (containsEveningPeak(hourStart, hourEnd)) {
            return true;
        }
        return false;
    }

    private boolean isEveningPeak(int hour) {
        if (hour >= EVENING_PEAK_START && hour < EVENING_PEAK_END) {
            return true;
        }
        return false;
    }

    private boolean isMorningPeak(int hour) {
        if (hour >= MORNING_PEAK_START && hour < MORNING_PEAK_END) {
                return true;
        }
        return false;
    }

    private boolean containsEveningPeak(int hourStart, int hourEnd) {
        if (hourStart <= EVENING_PEAK_START && hourEnd >= EVENING_PEAK_END) {
            return true;
        }
        return false;
    }

    private boolean containsMorningPeak(int hourStart, int hourEnd) {
        if (hourStart <= MORNING_PEAK_START && hourEnd >= MORNING_PEAK_END) {
            return true;
        }
        return false;
    }

    private int getCurrentHour(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    private boolean isLong(Journey journey){
        if(journey.durationSeconds() > LONG_JOURNEY_DURATION_IN_MINUTES * SECONDS_IN_A_MINUTE) {
            return true;
        }
        return false;
    }

    public BigDecimal calculatePriceOfJourney(Journey journey) {
        BigDecimal journeyPrice;
        if(journeyIsPeakTime(journey)){
            journeyPrice = getShortOrLongPeakFare(journey);
        } else {
            journeyPrice = getShortOrLongOffPeakFare(journey);
        }
        return journeyPrice;
    }

    private BigDecimal getShortOrLongPeakFare(Journey journey) {
        if(isLong(journey)) {
            return PEAK_LONG_JOURNEY_PRICE;
        } else {
            return PEAK_SHORT_JOURNEY_PRICE;
        }
    }

    private BigDecimal getShortOrLongOffPeakFare(Journey journey) {
        if(isLong(journey)) {
            return OFF_PEAK_LONG_JOURNEY_PRICE;
        } else {
            return OFF_PEAK_SHORT_JOURNEY_PRICE;
        }
    }
}
