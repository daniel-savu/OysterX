package com.tfl.billing;

/**
 * Created by bogdannitescu on 07/12/2017.
 */
public class JourneyHasNoEndException extends RuntimeException {
    public JourneyHasNoEndException() {
        super("Journey does not have an ending.");
    }
}