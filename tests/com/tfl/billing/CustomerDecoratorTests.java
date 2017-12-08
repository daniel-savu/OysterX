package com.tfl.billing;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.tfl.billing.Utility.dateFormatterToLong;

public class CustomerDecoratorTests {

    private UUID cardExampleID = UUID.randomUUID();
    private UUID readerOriginID = UUID.randomUUID();
    private UUID readerDestinationID = UUID.randomUUID();
    private JourneyStart journeyStart;
    private JourneyEnd journeyEnd;

    public Journey createTestJourneyWithStartTimeAndEndTime(String humanReadableStartTime, String humanReadableEndTime) throws InterruptedException {
        long startTime = dateFormatterToLong(humanReadableStartTime);
        long endTime = dateFormatterToLong(humanReadableEndTime);
        journeyStart = new JourneyStart(cardExampleID, readerOriginID, startTime);
        journeyEnd = new JourneyEnd(cardExampleID, readerDestinationID, endTime);
        return new Journey(journeyStart, journeyEnd);
    }

    @Test
    public void customerGetTotalIsCorrectForOneOffPeakShortJourney() throws InterruptedException {
        ArrayList<Journey> journeys = new ArrayList<>();
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:00:00","12:10:00"));
        List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
        CustomerDecorator customerDecorator = customerDecorators.get(0);
        BigDecimal expectedValue = new BigDecimal("1.6");
        Assert.assertTrue(BigDecimalCompare.equalTo(expectedValue, customerDecorator.getTotal(journeys)));
    }

    @Test
    public void customerGetTotalIsCorrectForOneOffPeakLongJourney() throws InterruptedException {
        ArrayList<Journey> journeys = new ArrayList<>();
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:00:00","12:30:00"));
        List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
        CustomerDecorator customerDecorator = customerDecorators.get(0);
        BigDecimal expectedValue = new BigDecimal("2.7");
        Assert.assertTrue(BigDecimalCompare.equalTo(expectedValue, customerDecorator.getTotal(journeys)));
    }

    @Test
    public void customerGetTotalIsCorrectForOnePeakShortJourney() throws InterruptedException {
        ArrayList<Journey> journeys = new ArrayList<>();
        journeys.add(createTestJourneyWithStartTimeAndEndTime("07:00:00","07:10:00"));
        List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
        CustomerDecorator customerDecorator = customerDecorators.get(0);
        BigDecimal expectedValue = new BigDecimal("2.90");
        Assert.assertTrue(BigDecimalCompare.equalTo(expectedValue, customerDecorator.getTotal(journeys)));
    }

    @Test
    public void customerGetTotalIsCorrectForOnePeakLongJourney() throws InterruptedException {
        ArrayList<Journey> journeys = new ArrayList<>();
        journeys.add(createTestJourneyWithStartTimeAndEndTime("07:00:00","07:30:00"));
        List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
        CustomerDecorator customerDecorator = customerDecorators.get(0);
        BigDecimal expectedValue = new BigDecimal("3.8");
        Assert.assertTrue(BigDecimalCompare.equalTo(expectedValue, customerDecorator.getTotal(journeys)));
    }


    @Test
    public void customerGetTotalIsCorrectForOffPeakCapAppliedJourneys() throws InterruptedException {
        ArrayList<Journey> journeys = new ArrayList<>();
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:00:00","12:10:00"));
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:15:00","12:45:00"));
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:50:00","12:55:00"));
        journeys.add(createTestJourneyWithStartTimeAndEndTime("13:00:00","13:05:00"));
        journeys.add(createTestJourneyWithStartTimeAndEndTime("13:10:00","14:00:00"));

        List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
        CustomerDecorator customerDecorator = customerDecorators.get(0);
        BigDecimal expectedValue = new BigDecimal("7.0");
        Assert.assertTrue(BigDecimalCompare.equalTo(expectedValue, customerDecorator.getTotal(journeys)));
    }

    @Test
    public void customerGetTotalIsCorrectForPeakCapAppliedJourneys() throws InterruptedException {
        ArrayList<Journey> journeys = new ArrayList<>();
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:00:00","12:10:00"));
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:15:00","12:45:00"));
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:50:00","12:55:00"));
        journeys.add(createTestJourneyWithStartTimeAndEndTime("13:00:00","13:05:00"));
        journeys.add(createTestJourneyWithStartTimeAndEndTime("18:10:00","19:00:00"));

        List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
        CustomerDecorator customerDecorator = customerDecorators.get(0);
        BigDecimal expectedValue = new BigDecimal("9.0");
        Assert.assertTrue(BigDecimalCompare.equalTo(expectedValue, customerDecorator.getTotal(journeys)));
    }

    @Test
    public void customerGetTotalIsCorrectForOneOffPeakShortJourneyAndOnePeakShortJourney() throws InterruptedException {
        ArrayList<Journey> journeys = new ArrayList<>();
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:00:00","12:10:00"));
        journeys.add(createTestJourneyWithStartTimeAndEndTime("07:00:00","07:10:00"));
        List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
        CustomerDecorator customerDecorator = customerDecorators.get(0);
        BigDecimal expectedValue = new BigDecimal("4.5");
        Assert.assertTrue(BigDecimalCompare.equalTo(expectedValue, customerDecorator.getTotal(journeys)));
    }

    @Test
    public void customerGetTotalIsCorrectForOneOffPeakLongJourneyAndOnePeakLongJourney() throws InterruptedException {
        ArrayList<Journey> journeys = new ArrayList<>();
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:00:00","12:30:00"));
        journeys.add(createTestJourneyWithStartTimeAndEndTime("07:00:00","07:30:00"));
        List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
        CustomerDecorator customerDecorator = customerDecorators.get(0);
        BigDecimal expectedValue = new BigDecimal("6.5");
        Assert.assertTrue(BigDecimalCompare.equalTo(expectedValue, customerDecorator.getTotal(journeys)));
    }

    @Test
    public void customerGetTotalIsCorrectForThreeOffPeakShortJourneysAndOnePeakLongJourney() throws InterruptedException{
        ArrayList<Journey> journeys = new ArrayList<>();
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:00:00","12:10:00"));
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:20:00","12:30:00"));
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:40:00","12:50:00"));
        journeys.add(createTestJourneyWithStartTimeAndEndTime("07:00:00","07:30:00"));
        List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
        CustomerDecorator customerDecorator = customerDecorators.get(0);
        BigDecimal expectedValue = new BigDecimal("8.6");
        Assert.assertTrue(BigDecimalCompare.equalTo(expectedValue, customerDecorator.getTotal(journeys)));
    }

}
