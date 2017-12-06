package com.tfl.billing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static com.sun.tools.javac.util.Constants.format;

public class Journey {

    private final JourneyEvent start;
    private final JourneyEvent end;
    Config config = new Config();

    public Journey(JourneyEvent start, JourneyEvent end) {
        this.start = start;
        this.end = end;
    }

    public String formattedStartTime() {
        return format(start.time());
    }

    public String formattedEndTime() {
        return format(end.time());
    }

    public UUID originId() {
        return start.readerId();
    }

    public UUID destinationId() {
        return end.readerId();
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
        ArrayList<Peak> peaks = config.getPeaks();
        for (Peak peak : peaks) {
            float journeyStartTime = Utility.dateTimeToFloatTime(this.startTime());
            float journeyEndTime = Utility.dateTimeToFloatTime(this.endTime());
            if (peak.contains(journeyStartTime) || peak.contains(journeyEndTime) || peak.isContainedInJourney(journeyStartTime,journeyEndTime)) {
                return true;
            }

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
            if (event instanceof JourneyEnd) {
                journeys.add(new Journey(start, event));
                start = null;
            }
        }
        return journeys;
    }

}
