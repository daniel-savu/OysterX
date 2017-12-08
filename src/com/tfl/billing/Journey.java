package com.tfl.billing;

import java.math.BigDecimal;
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

    public BigDecimal getPrice() {
        BigDecimal journeyPrice;
        if(this.isPeakTime()) {
            journeyPrice = this.getShortOrLongPeakFare();
        } else {
            journeyPrice = this.getShortOrLongOffPeakFare();
        }
        return journeyPrice;
    }

    public boolean isPeakTime() {
        ArrayList<Peak> peaks = config.getPeaks();
        for (Peak peak : peaks) {
            if (peak.contains(this.startTime()) || peak.contains(this.endTime()) || peak.isContainedInJourney(this.startTime(), this.endTime())) {
                return true;
            }
        }
        return false;
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

    public boolean isLong(){
        return (this.durationSeconds() > config.getLongJourneyDurationInMinutes() * config.getSecondsInAMinute());
    }

    static List<Journey> transformJourneyEventsToJourneys(List<JourneyEvent> customerJourneyEvents)  throws JourneyHasNoEndException {
        List<Journey> journeys = new ArrayList<>();
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

        if (start != null) throw new JourneyHasNoEndException();

        return journeys;
    }


    public String formattedStartTime() {
        return String.format("%s", start.time());
    }

    public String formattedEndTime() {
        return String.format("%s", start.time());
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

}
