package com.tfl.billing;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Journey {

    private final JourneyEvent start;
    private final JourneyEvent end;
    Config config = new Config();

    public Journey(JourneyEvent start, JourneyEvent end) {
        this.start = start;
        this.end = end;
    }

    public UUID originId() {
        return start.readerId();
    }

    public UUID destinationId() {
        return end.readerId();
    }

    public String formattedStartTime() {
        return format(start.time());
    }

    public String formattedEndTime() {
        return format(end.time());
    }

    public Date startTime() {
        return new Date(start.time());
    }

    public Date endTime() {
        return new Date(end.time());
    }

    public int durationSeconds() {
        return (int) ((end.time() - start.time()) / config.getMillisecondsInASecond());
    }

    public String durationMinutes() {
        return "" + durationSeconds() / config.getSecondsInAMinute() + ":" + durationSeconds() % config.getSecondsInAMinute();
    }

    private String format(long time) {
        return SimpleDateFormat.getInstance().format(new Date(time));
    }

    public BigDecimal getPrice() {
        BigDecimal journeyPrice;
        if(this.isPeakTime()){
            journeyPrice = this.getShortOrLongPeakFare();
        } else {
            journeyPrice = this.getShortOrLongOffPeakFare();
        }
        return journeyPrice;
    }

    private BigDecimal getShortOrLongPeakFare() {
        if(this.isLong()) {
            return config.getPeakLongJourneyPrice();
        } else {
            return config.getPeakShortJourneyPrice();
        }
    }

    private BigDecimal getShortOrLongOffPeakFare() {
        if(this.isLong()) {
            return config.getOffPeakLongJourneyPrice();
        } else {
            return config.getOffPeakShortJourneyPrice();
        }
    }

    public boolean isPeakTime() {
        return isPeak(this.startTime()) || isPeak(this.endTime()) || isPeak(this.startTime(), this.endTime());
    }

    public boolean isPeak(Date time) {
        int hour = Calculator.getCurrentHour(time);
        if (isMorningPeak(hour)) {
            return true;
        }
        if (isEveningPeak(hour)) {
            return true;
        }
        return false;
    }

    public boolean isPeak(Date timeStart, Date timeEnd) {
        int hourStart = Calculator.getCurrentHour(timeStart);
        int hourEnd = Calculator.getCurrentHour(timeEnd);

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

    public boolean isLong(){
        if(this.durationSeconds() > config.getLongJourneyDurationInMinutes() * config.getSecondsInAMinute()) {
            return true;
        }
        return false;
    }


    static List<Journey> transformJourneyEventsToJourneys(List<JourneyEvent> customerJourneyEvents) {
        List<Journey> journeys = new ArrayList<Journey>();
        JourneyEvent start = null;

        for (JourneyEvent event : customerJourneyEvents) {
            if (event instanceof JourneyStart) {
                start = event;
            }
            if (event instanceof JourneyEnd && start != null) {
                journeys.add(new Journey(start, event));
                start = null;
            }
        }
        return journeys;
    }

}
