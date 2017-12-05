package com.tfl.billing;

import java.math.BigDecimal;
import java.util.HashMap;

public class ConfigParser {

    final BigDecimal peakLongJourneyPrice;
    final BigDecimal peakShortJourneyPrice;
    final BigDecimal offPeakLongJourneyPrice;
    final BigDecimal offPeakShortJourneyPrice;
    final int longJourneyDurationInMinutes;
    final int secondsInAMinute;
    final int morningPeakStart;
    final int morningPeakEnd;
    final int eveningPeakStart;
    final int eveningPeakEnd;
    HashMap<String, String> rawConstants = new HashMap<>();

    public ConfigParser() {
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

    public HashMap<String, String> getRawConstants() {
        return rawConstants;
    }
}
