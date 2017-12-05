package com.tfl.billing;

import java.math.BigDecimal;

public class BigDecimalCompare {
    public static boolean greaterThan(BigDecimal a, BigDecimal b) {
        if(a.compareTo(b) == 1) {
            return true;
        }
        return false;
    }

    public static boolean equalTo(BigDecimal a, BigDecimal b) {
        if(a.compareTo(b) == 0) {
            return true;
        }
        return false;
    }

    public static boolean smallerThan(BigDecimal a, BigDecimal b) {
        if(a.compareTo(b) == -1) {
            return true;
        }
        return false;
    }

}
