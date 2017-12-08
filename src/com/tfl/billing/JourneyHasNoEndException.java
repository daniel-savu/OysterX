package com.tfl.billing;

public class JourneyHasNoEndException extends RuntimeException {
    public JourneyHasNoEndException() {
        super("Journey does not have an ending.");
    }
}