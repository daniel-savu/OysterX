package com.tfl.billing;

import com.oyster.OysterCard;
import com.oyster.OysterCardReader;
import com.tfl.underground.OysterReaderLocator;
import com.tfl.underground.Station;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;
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
        OysterCard myCard = new OysterCard("38400000-8cf0-11bd-b23e-10b96e4ef00d");

        OysterCardReader paddingtonReader = OysterReaderLocator.atStation(Station.PADDINGTON);
        OysterCardReader bakerStreetReader = OysterReaderLocator.atStation(Station.BAKER_STREET);
        OysterCardReader kingsCrossReader = OysterReaderLocator.atStation(Station.KINGS_CROSS);

        TravelTracker travelTracker = new TravelTracker();
        travelTracker.connect(paddingtonReader, bakerStreetReader, kingsCrossReader);

        paddingtonReader.touch(myCard);
        bakerStreetReader.touch(myCard);
        bakerStreetReader.touch(myCard);
        kingsCrossReader.touch(myCard);

        List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
        for (CustomerDecorator customerDecorator : customerDecorators) {
            if(BigDecimalCompare.equalTo(customerDecorator.getTotal(), new BigDecimal("5.8"))) {
                Assert.assertEquals(customerDecorator.fullName(), "Fred Bloggs");
            }
        }
       //
    }

    @Test
    public void customerGetTotalReturnValueIsCorrect() throws InterruptedException {
        List<Journey> journeys = null;
        journeys.add(createTestJourneyWithStartTimeAndEndTime("12:00:00","12:10:00"));
        List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
        CustomerDecorator customerDecorator = customerDecorators.get(0);
        Assert.assertEquals(1.60,customerDecorator.getTotal(journeys));
    }
}
