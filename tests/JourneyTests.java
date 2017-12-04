package com.tfl.billing;

import org.junit.*;

import java.util.UUID;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by bogdannitescu on 27/11/2017.
 */
public class JourneyTests {

    private UUID cardExampleID = UUID.randomUUID();
    private UUID readerOriginID = UUID.randomUUID();
    private UUID readerDestinationID = UUID.randomUUID();
    private JourneyStart journeyStart;
    private JourneyEnd journeyEnd;
    private Journey journey;

    private void createTestJourney(int secondsToSleep) throws InterruptedException
    {
        journeyStart = new JourneyStart(cardExampleID, readerOriginID);
        Thread.sleep(secondsToSleep * 1000);
        journeyEnd = new JourneyEnd(cardExampleID, readerDestinationID);
        journey = new Journey(journeyStart, journeyEnd);
    }

    @Test
    public void journeyStartStaionIdEqualsOriginId() throws InterruptedException
    {
        createTestJourney(1);
        Assert.assertEquals(journey.originId(), journeyStart.readerId());
    }

    @Test
    public void journeyEndStationIdEqualsDestinationId() throws InterruptedException
    {
        createTestJourney(2);
        Assert.assertEquals(journey.destinationId(),journeyEnd.readerId());
    }

    @Test
    public void journeyStartTimeIsBeforeJourneyEndTime() throws InterruptedException
    {
        createTestJourney(1);
        assertTrue(journey.startTime().before(journey.endTime()));
    }
}
