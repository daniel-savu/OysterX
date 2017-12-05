package com.tfl.billing;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class Calculator {
    private static final BigDecimal PEAK_LONG_JOURNEY_PRICE = new BigDecimal(3.80);
    private static final BigDecimal PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(2.90);
    private static final BigDecimal OFF_PEAK_LONG_JOURNEY_PRICE = new BigDecimal(2.70);
    private static final BigDecimal OFF_PEAK_SHORT_JOURNEY_PRICE = new BigDecimal(1.60);

    public boolean isPeak(Journey journey) {
        return peak(journey.startTime()) || peak(journey.endTime());
    }

    public boolean peak(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return (hour >= 6 && hour <= 9) || (hour >= 17 && hour <= 19);
    }

    private boolean isLong(Journey journey){
        if(journey.durationSeconds() > 25*60)
            return true;
        return false;
    }

    public BigDecimal calculatePriceOfJourney(Journey journey){
        BigDecimal journeyPrice;
        if(isPeak(journey)){
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
