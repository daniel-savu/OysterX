package com.tfl.billing;

import java.math.BigDecimal;
import java.util.HashMap;

public class Config {

    private final BigDecimal peakLongJourneyPrice;
    private final BigDecimal peakShortJourneyPrice;
    private final BigDecimal offPeakLongJourneyPrice;
    private final BigDecimal offPeakShortJourneyPrice;
    private final int longJourneyDurationInMinutes;
    private final int secondsInAMinute;
    private final int morningPeakStart;
    private final int morningPeakEnd;
    private final int eveningPeakStart;
    private final int eveningPeakEnd;
    private final BigDecimal offPeakCap;
    private final BigDecimal peakCap;

    private final int millisecondsInASecond;
    private HashMap<String, String> rawConstants = new HashMap<>();

    public Config() {
        ConfigReader configReader = new ConfigReader();
        rawConstants = configReader.getRawConstants();

        String rawPeakLongJourneyPrice = rawConstants.get("PEAK_LONG_JOURNEY_PRICE");
        peakLongJourneyPrice = new BigDecimal(rawPeakLongJourneyPrice);

        String rawPeakShortJourneyPrice = rawConstants.get("PEAK_SHORT_JOURNEY_PRICE");
        peakShortJourneyPrice = new BigDecimal(rawPeakShortJourneyPrice);

        String rawOffPeakLongJourneyPrice = rawConstants.get("OFF_PEAK_LONG_JOURNEY_PRICE");
        offPeakLongJourneyPrice = new BigDecimal(rawOffPeakLongJourneyPrice);

        String rawOffPeakShortJourneyPrice = rawConstants.get("OFF_PEAK_SHORT_JOURNEY_PRICE");
        offPeakShortJourneyPrice = new BigDecimal(rawOffPeakShortJourneyPrice);

        String rawLongJourneyDurationInMinutes = rawConstants.get("LONG_JOURNEY_DURATION_IN_MINUTES");
        longJourneyDurationInMinutes = Integer.parseInt(rawLongJourneyDurationInMinutes);

        String rawSecondsInAMinute = rawConstants.get("SECONDS_IN_A_MINUTE");
        secondsInAMinute = Integer.parseInt(rawSecondsInAMinute);

        String rawMorningPeakStart = rawConstants.get("MORNING_PEAK_START");
        morningPeakStart = Integer.parseInt(rawMorningPeakStart);

        String rawMorningPeakEnd = rawConstants.get("MORNING_PEAK_END");
        morningPeakEnd = Integer.parseInt(rawMorningPeakEnd);

        String rawEveningPeakStart = rawConstants.get("EVENING_PEAK_START");
        eveningPeakStart = Integer.parseInt(rawEveningPeakStart);

        String rawEveningPeakEnd = rawConstants.get("EVENING_PEAK_END");
        eveningPeakEnd = Integer.parseInt(rawEveningPeakEnd);

        String rawOffPeakCap = rawConstants.get("OFF_PEAK_CAP");
        offPeakCap = new BigDecimal(rawOffPeakCap);

        String rawPeakCap = rawConstants.get("PEAK_CAP");
        peakCap = new BigDecimal(rawPeakCap);

        String rawMillisecondsInASecond = rawConstants.get("MILLISECONDS_IN_A_SECOND");
        millisecondsInASecond = Integer.parseInt(rawMillisecondsInASecond);
    }

    public BigDecimal getPeakLongJourneyPrice() {
        return peakLongJourneyPrice;
    }

    public BigDecimal getPeakShortJourneyPrice() {
        return peakShortJourneyPrice;
    }

    public BigDecimal getOffPeakLongJourneyPrice() {
        return offPeakLongJourneyPrice;
    }

    public BigDecimal getOffPeakShortJourneyPrice() {
        return offPeakShortJourneyPrice;
    }

    public int getLongJourneyDurationInMinutes() {
        return longJourneyDurationInMinutes;
    }

    public int getSecondsInAMinute() {
        return secondsInAMinute;
    }

    public int getMorningPeakStart() {
        return morningPeakStart;
    }

    public int getMorningPeakEnd() {
        return morningPeakEnd;
    }

    public int getEveningPeakStart() {
        return eveningPeakStart;
    }

    public int getEveningPeakEnd() {
        return eveningPeakEnd;
    }

    public BigDecimal getOffPeakCap() {
        return offPeakCap;
    }

    public BigDecimal getPeakCap() {
        return peakCap;
    }

    public int getMillisecondsInASecond() {
        return millisecondsInASecond;
    }
}
