package com.tfl.billing;

import java.text.SimpleDateFormat;
import java.util.Date;
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
}
