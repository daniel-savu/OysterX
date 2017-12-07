package com.tfl.billing;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;
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
    public void customerGetTotalIsCorrect() throws InterruptedException {
        ArrayList<Journey> journeys = new ArrayList<>();
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:00:00","12:10:00"));
        List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
        CustomerDecorator customerDecorator = customerDecorators.get(0);
        BigDecimal expectedValue = new BigDecimal("1.6");
        Assert.assertTrue(BigDecimalCompare.equalTo(expectedValue, customerDecorator.getTotal(journeys)));
    }
}
