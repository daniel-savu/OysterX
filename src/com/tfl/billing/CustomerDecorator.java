package com.tfl.billing;

import com.oyster.OysterCard;
import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CustomerDecorator extends Customer {
    Customer customer;
    Calculator calculator = new Calculator();
    Config config = new Config();
    private boolean travelledOnPeakTime;

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
        List<Journey> journeys = getJourneys();
        BigDecimal customerTotal = getTotal(journeys);
        manageTransaction(journeys, customerTotal);
    }

    List<Journey> getJourneys() {
        List<JourneyEvent> customerJourneyEvents = collectCustomerJourneyEvents();
        return Journey.transformJourneyEventsToJourneys(customerJourneyEvents);
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

    private BigDecimal getTotal(List<Journey> journeys) {
        BigDecimal customerTotal = new BigDecimal("0");
        BigDecimal priceOfJourney = null;

        for (Journey journey : journeys) {
            priceOfJourney = journey.getPrice();
            travelledOnPeakTime = journey.isPeakTime();

            try{
                customerTotal = customerTotal.add(priceOfJourney);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        customerTotal = applyCapIfNeeded(customerTotal, travelledOnPeakTime);
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
