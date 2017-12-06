package com.tfl.billing;

import java.math.BigDecimal;

public class BigDecimalCompare {
    private BigDecimalCompare() {

    }
    public static boolean greaterThan(BigDecimal a, BigDecimal b) {
        return (a.compareTo(b) > 0);
    }

    public static boolean equalTo(BigDecimal a, BigDecimal b) {
        return (a.compareTo(b) == 0);
    }

    public static boolean smallerThan(BigDecimal a, BigDecimal b) {
        return (a.compareTo(b) < 0);
    }
}
