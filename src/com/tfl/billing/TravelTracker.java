package com.tfl.billing;

import com.oyster.OysterCardReader;
import com.oyster.ScanListener;

import java.util.*;

public class TravelTracker implements ScanListener {


    static final List<JourneyEvent> eventLog = new ArrayList<>();
    private final Set<UUID> currentlyTravelling = new HashSet<>();



    public void connect(OysterCardReader... cardReaders) {
        for (OysterCardReader cardReader : cardReaders) {
            cardReader.register(this);
        }
    }

    @Override
    public void cardScanned(UUID cardId, UUID readerId) {
        if (currentlyTravelling.contains(cardId)) {
            manageJourneyEnd(cardId, readerId);
        } else {
            manageJourneyStart(cardId, readerId);
        }
    }

    private void manageJourneyEnd(UUID cardId, UUID readerId) {
        JourneyEnd journeyEnd = new JourneyEnd(cardId, readerId);
        eventLog.add(journeyEnd);
        currentlyTravelling.remove(cardId);
    }

    private void manageJourneyStart(UUID cardId, UUID readerId) {
        if (DatabaseController.isCardIdRegistered(cardId)) {
            currentlyTravelling.add(cardId);
            eventLog.add(new JourneyStart(cardId, readerId));
        } else {
            throw new UnknownOysterCardException(cardId);
        }
    }

}
