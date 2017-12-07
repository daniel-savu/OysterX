package com.tfl.billing;

import com.oyster.OysterCard;
import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CustomerDecorator extends Customer {
    Customer customer;
    Config config = new Config();
    private boolean travelledOnPeakTime;
    List<Journey> journeys;

    public CustomerDecorator(Customer customer) {
        super(customer.fullName(), new OysterCard(customer.cardId().toString()));
        this.customer = customer;
        travelledOnPeakTime = false;
    }

    public CustomerDecorator(String fullName, OysterCard oysterCard) {
        super(fullName, oysterCard);
        travelledOnPeakTime = false;
    }

    void makePayment () {
        BigDecimal customerTotal = getTotal();
        manageTransaction(customerTotal);
    }

    public BigDecimal getTotal() {
        journeys = getJourneys();
        BigDecimal customerTotal = new BigDecimal("0");
        BigDecimal priceOfJourney = null;

        for (Journey journey : journeys) {
            priceOfJourney = journey.getPrice();
            travelledOnPeakTime = journey.isPeakTime();

            try{
                customerTotal = customerTotal.add(priceOfJourney);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        customerTotal = applyCapIfNeeded(customerTotal, travelledOnPeakTime);
        return customerTotal;
    }

    List<Journey> getJourneys() {
        List<JourneyEvent> customerJourneyEvents = collectCustomerJourneyEvents();
        return Journey.transformJourneyEventsToJourneys(customerJourneyEvents);
    }

    private List<JourneyEvent> collectCustomerJourneyEvents() {
        List<JourneyEvent> customerJourneyEvents = new ArrayList<>();
        for (JourneyEvent journeyEvent : TravelTracker.eventLog) {
            if (journeyEvent.cardId().equals(customer.cardId())) {
                customerJourneyEvents.add(journeyEvent);
            }
        }
        return customerJourneyEvents;
    }

    private void manageTransaction (BigDecimal customerTotal) {
        PaymentsSystem.getInstance().charge(customer, journeys, Utility.roundToNearestPenny(customerTotal));
    }

    private BigDecimal applyCapIfNeeded(BigDecimal customerTotal, boolean customerTravelledOnPeakTime) {
        if (customerTravelledOnPeakTime) {
            customerTotal = applyPeakCapIfNeeded(customerTotal);
        } else {
            customerTotal = applyOffPeakCapIfNeeded(customerTotal);
        }
        return customerTotal;
    }

    private BigDecimal applyPeakCapIfNeeded(BigDecimal customerTotal) {
        if (BigDecimalCompare.greaterThan(customerTotal, config.getPeakCap())) {
            customerTotal = config.getPeakCap();
        }
        return customerTotal;
    }

    private BigDecimal applyOffPeakCapIfNeeded(BigDecimal customerTotal) {
        if (BigDecimalCompare.greaterThan(customerTotal, config.getOffPeakCap())) {
            customerTotal = config.getOffPeakCap();
        }
        return customerTotal;
    }
}
