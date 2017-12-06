package com.tfl.billing;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.fail;


public class TravelTrackerTests {

    private UUID cardExampleID = UUID.randomUUID();
    private UUID readerOriginID = UUID.randomUUID();
    private UUID readerDestinationID = UUID.randomUUID();

    TravelTracker travelTracker = new TravelTracker();

    @Test
    public void scanningUnknownCardIDThrowsUnknownOysterCardException() {
        UUID unknownOysterCardID = UUID.randomUUID();
        try {
            travelTracker.cardScanned(unknownOysterCardID, readerOriginID);
            fail("UnknownOysterCardException not thrown");
        } catch(Exception e) {
            Assert.assertTrue(e.getClass().getCanonicalName().equals("com.tfl.billing.UnknownOysterCardException"));
        }
    }

    @Test
    public void scanningKnownCardIDDoesNotThrowException() {
        UUID knownOysterCardID = UUID.fromString("89adbd1c-4de6-40e5-98bc-b3315c6873f2");
        try {
            travelTracker.cardScanned(knownOysterCardID, readerOriginID);

        } catch(Exception e) {
            fail("UnknownOysterCardException not thrown");
        }
    }


}
