package com.tfl.billing;

public class WrongTimeFormatException extends RuntimeException {
    public WrongTimeFormatException() {
        super("Either a wrong separator was used for the time dateFormatterToLong in config.csv file, or the time dateFormatterToLong did not respect" +
                "the hh:mm:ss dateFormatterToLong");
    }
}
