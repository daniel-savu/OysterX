package com.tfl.billing;

public class WrongTimeSeparatorException extends RuntimeException {
    public WrongTimeSeparatorException() {
        super("Either a wrong separator was used for the time dateFormatterToLong in config.csv file, or the time dateFormatterToLong did not respect" +
                "the hh:mm:ss dateFormatterToLong");
    }
}
