package com.tfl.billing;

public class ExceptionIsJourneyEndIsBeforeStart extends Exception {

    public ExceptionIsJourneyEndIsBeforeStart(){
        super();
    }

    public ExceptionIsJourneyEndIsBeforeStart (String message){
        super ("Your journey can't End before it Starts!");
    }

}