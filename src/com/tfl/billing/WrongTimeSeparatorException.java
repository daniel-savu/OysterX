package com.tfl.billing;

public class WrongTimeSeparatorException extends RuntimeException {
    public WrongTimeSeparatorException() {
        super("Either a wrong separator was used for the time format in config.csv file, or the time format did not respect" +
                "the hh:mm:ss format");
    }
}
