package com.tfl.billing;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class Calculator {

    Config config = new Config();
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

    private boolean isMorningPeak(int hour) {
        if (hour >= config.getMorningPeakStart() && hour < config.getMorningPeakEnd()) {
            return true;
        }
        return false;
    }

    private boolean isEveningPeak(int hour) {
        if (hour >= config.getEveningPeakStart() && hour < config.getEveningPeakEnd()) {
            return true;
        }
        return false;
    }

    private boolean containsMorningPeak(int hourStart, int hourEnd) {
        if (hourStart <= config.getMorningPeakStart() && hourEnd >= config.getMorningPeakEnd()) {
            return true;
        }
        return false;
    }

    private boolean containsEveningPeak(int hourStart, int hourEnd) {
        if (hourStart <= config.getEveningPeakStart() && hourEnd >= config.getEveningPeakEnd()) {
            return true;
        }
        return false;
    }

    private int getCurrentHour(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public boolean isLong(Journey journey){
        if(journey.durationSeconds() > config.getLongJourneyDurationInMinutes() * config.getSecondsInAMinute()) {
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
            return config.getPeakLongJourneyPrice();
        } else {
            return config.getPeakShortJourneyPrice();
        }
    }

    private BigDecimal getShortOrLongOffPeakFare(Journey journey) {
        if(isLong(journey)) {
            return config.getOffPeakLongJourneyPrice();
        } else {
            return config.getOffPeakShortJourneyPrice();
        }
    }
}
