package com.tfl.billing;

import com.oyster.OysterCard;
import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CustomerDecorator extends Customer {
    boolean travelledOnPeakTime;
    Customer customer;
    Calculator calculator = new Calculator();
    Config config = new Config();

    public CustomerDecorator(Customer customer) {
        super(customer.fullName(), new OysterCard(customer.cardId().toString()));
        this.customer = customer;
    }

    public CustomerDecorator(String fullName, OysterCard oysterCard) {
        super(fullName, oysterCard);
    }

    void makePayment () {
        List<Journey> journeys = getJourneys();
        BigDecimal customerTotal = getTotal(journeys);
        manageTransaction(journeys, customerTotal);
    }

    List<Journey> getJourneys() {
        List<JourneyEvent> customerJourneyEvents = collectCustomerJourneyEvents();
        return transformJourneyEventsToJourneys(customerJourneyEvents);
    }

    List<JourneyEvent> collectCustomerJourneyEvents() {
        List<JourneyEvent> customerJourneyEvents = new ArrayList<JourneyEvent>();
        for (JourneyEvent journeyEvent : TravelTracker.eventLog) {
            if (journeyEvent.cardId().equals(customer.cardId())) {
                customerJourneyEvents.add(journeyEvent);
            }
        }
        return customerJourneyEvents;
    }

    private List<Journey> transformJourneyEventsToJourneys(List<JourneyEvent> customerJourneyEvents) {
        List<Journey> journeys = new ArrayList<Journey>();
        JourneyEvent start = null;

        for (JourneyEvent event : customerJourneyEvents) {
            if (event instanceof JourneyStart) {
                start = event;
            }
            if (event instanceof JourneyEnd && start != null) {
                journeys.add(new Journey(start, event));
                start = null;
            }
        }
        return journeys;
    }

    private BigDecimal getTotal(List<Journey> journeys) {
        BigDecimal customerTotal = new BigDecimal("0");
        BigDecimal priceOfJourney = null;
        boolean customerTravelledOnPeakTime = false;

        for (Journey journey : journeys) {
            priceOfJourney = journey.getPrice();
            customerTravelledOnPeakTime = journey.isPeakTime();

            try{
                customerTotal = customerTotal.add(priceOfJourney);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        customerTotal = applyCapIfNeeded(customerTotal, customerTravelledOnPeakTime);
        return customerTotal;
    }

    private void manageTransaction (List<Journey> journeys, BigDecimal customerTotal) {
        PaymentsSystem.getInstance().charge(customer, journeys, calculator.roundToNearestPenny(customerTotal));
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
