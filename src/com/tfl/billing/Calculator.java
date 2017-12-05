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
        if (hour >= 17 && hour < 20) {
            return true;
        }
        return false;
    }

    private boolean isMorningPeak(int hour) {
        if (hour >= 6 && hour < 10) {
                return true;
        }
        return false;
    }

    private boolean containsEveningPeak(int hourStart, int hourEnd) {
        if (hourStart <= 17 && hourEnd >= 20) {
            return true;
        }
        return false;
    }

    private boolean containsMorningPeak(int hourStart, int hourEnd) {
        if (hourStart <= 6 && hourEnd >= 10) {
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
        if(journey.durationSeconds() > LONG_JOURNEY_DURATION_IN_MINUTES*60) {
            return true;
        }
        return false;
    }

    public BigDecimal calculatePriceOfJourney(Journey journey) {
        BigDecimal journeyPrice;
        if(journeyIsPeakTime(journey)){
            journeyPrice = getShortLongPeakFare(journey);
        } else {
            journeyPrice = getShortLongOffPeakFare(journey);
        }
        return journeyPrice;
    }

    private BigDecimal getShortLongPeakFare(Journey journey) {
        if(isLong(journey)) {
            return PEAK_LONG_JOURNEY_PRICE;
        } else {
            return PEAK_SHORT_JOURNEY_PRICE;
        }
    }

    private BigDecimal getShortLongOffPeakFare(Journey journey) {
        if(isLong(journey)) {
            return OFF_PEAK_LONG_JOURNEY_PRICE;
        } else {
            return OFF_PEAK_SHORT_JOURNEY_PRICE;
        }
    }
}
