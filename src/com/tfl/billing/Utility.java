package com.tfl.billing;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Utility {

    public static int getCurrentHour(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {
        return poundsAndPence.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    public static float stringTimeToFloatTime(String time) {
        final String timeSeparator = ":";
        if(!time.contains(timeSeparator)) {
            throw new WrongTimeFormatException();
        }
        String[] timeComponents = time.split(timeSeparator);
        if (timeComponents.length != 3) {
            throw new WrongTimeFormatException();
        }
        float hour = Float.parseFloat(timeComponents[0]);
        float minute = Float.parseFloat(timeComponents[1]);
        return (float) (hour + (minute/60.0));
    }

    public static float dateTimeToFloatTime(Date time) {
        float hour = time.getHours();
        float minutes = time.getMinutes();
        return (float) (hour + (minutes/60.0));
    }

    public static long dateFormatterToLong(String humanReadableTime) {
        DateFormat formatter = new SimpleDateFormat("hh:mm:ss");
        try {
            Date date = formatter.parse(humanReadableTime);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        throw new WrongTimeFormatException();
    }
}

