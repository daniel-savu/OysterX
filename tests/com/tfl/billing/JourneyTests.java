package com.tfl.billing;

import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;

import java.util.UUID;

import static junit.framework.TestCase.assertTrue;

public class JourneyTests {

    private UUID cardExampleID = UUID.randomUUID();
    private UUID readerOriginID = UUID.randomUUID();
    private UUID readerDestinationID = UUID.randomUUID();
    private JourneyStart journeyStart;
    private JourneyEnd journeyEnd;
    private Journey journey;

    void createTestJourneyWithStartTimeAndEndTime(String humanReadableStartTime, String humanReadableEndTime) throws InterruptedException {
        long startTime = DateFormatter.format(humanReadableStartTime);
        long endTime = DateFormatter.format(humanReadableEndTime);
        journeyStart = new JourneyStart(cardExampleID, readerOriginID, startTime);
        journeyEnd = new JourneyEnd(cardExampleID, readerDestinationID, endTime);
        journey = new Journey(journeyStart, journeyEnd);
    }

    @Rule
    public JUnitRuleMockery context = new JUnitRuleMockery();



    @Test
    public void journeyStartStaionIdEqualsOriginId() throws InterruptedException
    {
        createTestJourneyWithStartTimeAndEndTime("07:30:00","07:45:00");
        Assert.assertEquals(journey.originId(), journeyStart.readerId());
    }

    @Test
    public void journeyEndStationIdEqualsDestinationId() throws InterruptedException
    {
        createTestJourneyWithStartTimeAndEndTime("07:30:00","07:45:00");
        Assert.assertEquals(journey.destinationId(),journeyEnd.readerId());
    }

    @Test
    public void journeyStartTimeIsBeforeJourneyEndTime() throws InterruptedException
    {
        createTestJourneyWithStartTimeAndEndTime("07:30:00","07:45:00");
        assertTrue(journey.startTime().before(journey.endTime()));
    }


}
