package com.tfl.billing;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class Utility {

    Config config = new Config();

    public static int getCurrentHour(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {
        return poundsAndPence.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static float stringTimeToFloatTime(String time) throws WrongTimeSeparatorException {
        final String timeSeparator = ":";
        String[] timeComponents = time.split(timeSeparator);
        if (timeComponents.length != 3) {
            throw new WrongTimeSeparatorException();
        }
        float hour = Float.parseFloat(timeComponents[0]);
        float minute = Float.parseFloat(timeComponents[1]);
        return (float) (hour + (minute/60.0));
    }

    public static float dateTimeToFloatTime(Date time) {
        float hour = time.getHours();
        float minutes = time.getMinutes();
        return (float) ((float) hour + (minutes/60.0));
    }
}
