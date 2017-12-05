package com.tfl.billing;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Created by bogdannitescu on 05/12/2017.
 */
public class CalculatorTest {
    private UUID cardExampleID = UUID.randomUUID();
    private UUID readerOriginID = UUID.randomUUID();
    private UUID readerDestinationID = UUID.randomUUID();
    private JourneyStart journeyStart;
    private JourneyEnd journeyEnd;
    private Journey journey;

    private Calculator calculator = new Calculator();

    void createTestJourneyWithStartTimeAndEndTime(String humanReadableStartTime, String humanReadableEndTime) throws InterruptedException {
        long startTime = DateFormatter.format(humanReadableStartTime);
        long endTime = DateFormatter.format(humanReadableEndTime);
        journeyStart = new JourneyStart(cardExampleID, readerOriginID, startTime);
        journeyEnd = new JourneyEnd(cardExampleID, readerDestinationID, endTime);
        journey = new Journey(journeyStart, journeyEnd);
    }

    @Test
    public void journeyStartBefore6AMAndJourneyEndBetween6And10AMDetectsAsPeak() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("05:45:00","07:00:00");
        Assert.assertTrue(calculator.journeyIsPeakTime(journey));
    }

    @Test
    public void journeyStartsAfter6AMAndJourneyEndsBefore10AMDetectsAsPeak() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("06:30:00","08:00:00");
        Assert.assertTrue(calculator.journeyIsPeakTime(journey));
    }

    @Test
    public void journeyStartsBefore10AMAndFinishesAfter10AMDetectsAsPeak() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("09:30:00","10:30:00");
        Assert.assertTrue(calculator.journeyIsPeakTime(journey));
    }

    @Test
    public void journeyStartsAndFinishesBetween10AMAnd17PMDetectsOffPeak() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("10:30:00","12:00:00");
        Assert.assertFalse(calculator.journeyIsPeakTime(journey));
    }

    @Test
    public void journeyStartsBefore17PMAndFinishesBetween17And20PMDetectsAsPeak() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("16:30:00","17:30:00");
        Assert.assertTrue(calculator.journeyIsPeakTime(journey));
    }

    @Test
    public void journeyStartsAndFinishesBetween17And20PMDetectsAsPeak() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("17:30:00","18:30:00");
        Assert.assertTrue(calculator.journeyIsPeakTime(journey));
    }

    @Test
    public void journeyStartsBefore20PMandFinishesAfter20PMDetectsAsPeak() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("19:30:00","20:30:00");
        Assert.assertTrue(calculator.journeyIsPeakTime(journey));
    }

    @Test
    public void journeyStartsAfter20PMDetectsOffPeak() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("20:30:00","21:30:00");
        Assert.assertFalse(calculator.journeyIsPeakTime(journey));
    }

    @Test
    public void twentyMinutesJourneyIsShort() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("20:30:00","20:50:00");
        Assert.assertFalse(calculator.isLong(journey));
    }

    @Test
    public void thrityMinutesJourneyIsLong() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("20:20:00","20:50:00");
        Assert.assertTrue(calculator.isLong(journey));
    }

    @Test
    public void journeyStartsBefore6AMAndEndsAfter10AMDetectedAsPeak() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("05:55:00","10:05:00");
        Assert.assertTrue(calculator.journeyIsPeakTime(journey));
    }

    @Test
    public void journeyStartsBefore17PMAndEndsAfter20PMDetectedAsPeakTime() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("16:55:00","20:05:00");
        Assert.assertTrue(calculator.journeyIsPeakTime(journey));
    }

    @Test
    public void shortOffPeakJourneyPriceIsCorrect() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("12:00:00","12:10:00");
        Assert.assertEquals(new BigDecimal("1.6"),calculator.calculatePriceOfJourney(journey));
    }

    @Test
    public void shortPeakJourneyPriceIsCorrect() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("08:00:00","08:10:00");
        Assert.assertEquals(new BigDecimal("2.9"), calculator.calculatePriceOfJourney(journey));
    }

    @Test
    public void longOffPeakJourneyPriceIsCorrect() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("12:00:00","12:40:00");
        Assert.assertEquals(new BigDecimal("2.7"), calculator.calculatePriceOfJourney(journey));
    }


    @Test
    public void longPeakJourneyPriceIsCorrect() throws InterruptedException {
        createTestJourneyWithStartTimeAndEndTime("08:00:00","08:40:00");
        Assert.assertEquals(new BigDecimal("3.8"), calculator.calculatePriceOfJourney(journey));
    }

}