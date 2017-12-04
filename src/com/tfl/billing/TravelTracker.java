package com.tfl.billing;

import com.oyster.OysterCardReader;
import com.oyster.ScanListener;
import com.tfl.external.Customer;

import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.*;

public class TravelTracker implements ScanListener {

    static final BigDecimal OFF_PEAK_JOURNEY_PRICE = new BigDecimal(2.40);
    static final BigDecimal PEAK_JOURNEY_PRICE = new BigDecimal(3.20);

    final List<JourneyEvent> eventLog = new ArrayList<JourneyEvent>();
    private final Set<UUID> currentlyTravelling = new HashSet<UUID>();


    private boolean peak(Journey journey) {
        return peak(journey.startTime()) || peak(journey.endTime());
    }

    private boolean peak(Date time) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return (hour >= 6 && hour <= 9) || (hour >= 17 && hour <= 19);
    }

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
        if (isCardIdRegistered(cardId)) {
            currentlyTravelling.add(cardId);
            eventLog.add(new JourneyStart(cardId, readerId));
        } else {
            throw new UnknownOysterCardException(cardId);
        }
    }

}
