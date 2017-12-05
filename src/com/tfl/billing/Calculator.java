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

        return peak(journey.startTime()) || peak(journey.endTime());
    }

    public boolean peak(Date time) {
        int hour = getCurrentHour(time);
        if (morningPeak(hour)) return true;
        if (eveningPeak(hour)) return true;
        return false;
    }

    private boolean eveningPeak(int hour) {
        if (hour >= 17) if (hour < 20) return true;
        return false;
    }

    private boolean morningPeak(int hour) {
        if (hour >= 6) if (hour < 10) return true;
        return false;
    }

    private int getCurrentHour(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    private boolean isLong(Journey journey){
        if(journey.durationSeconds() > LONG_JOURNEY_DURATION_IN_MINUTES*60)
            return true;
        return false;
    }

    public BigDecimal calculatePriceOfJourney(Journey journey){
        BigDecimal journeyPrice;
        if(journeyIsPeakTime(journey)){
            if(isLong(journey))
                journeyPrice = PEAK_LONG_JOURNEY_PRICE;
            else
                journeyPrice = PEAK_SHORT_JOURNEY_PRICE;
        } else {
            if(isLong(journey))
                journeyPrice = OFF_PEAK_LONG_JOURNEY_PRICE;
            else
                journeyPrice = OFF_PEAK_SHORT_JOURNEY_PRICE;
        }
        return journeyPrice;
    }
}
