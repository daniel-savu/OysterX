package com.tfl.billing;

public class JourneyEndIsBeforeStartException extends Exception {

    public JourneyEndIsBeforeStartException(){
        super();
    }

    public JourneyEndIsBeforeStartException(String message){
        super ("Your journey can't End before it Starts!");
    }

}