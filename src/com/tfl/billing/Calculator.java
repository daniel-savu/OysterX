package com.tfl.billing;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class Calculator {

    Config config = new Config();

    public static int getCurrentHour(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {
        return poundsAndPence.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
