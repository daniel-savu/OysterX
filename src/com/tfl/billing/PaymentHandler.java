package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class PaymentHandler {
    public void chargeAccounts() {
        List<Customer> customers = getCustomers();
        for (Customer customer : customers) {
            makePayment(customer);
        }
    }

    void makePayment (Customer customer) {
        List<JourneyEvent> customerJourneyEvents = collectCustomerJourneyEvents(customer);
        List<Journey> journeys = transformJourneyEventsToJourneys(customerJourneyEvents);
        BigDecimal customerTotal = calculateDailyCustomerPayment(journeys);
        manageTransaction(customer,journeys, customerTotal);
    }

    List<JourneyEvent> collectCustomerJourneyEvents(Customer customer) {
        List<JourneyEvent> customerJourneyEvents = new ArrayList<JourneyEvent>();
        for (JourneyEvent journeyEvent : eventLog) {
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



    {

        BigDecimal customerTotal = new BigDecimal(0);
        for (Journey journey : journeys) {
            BigDecimal journeyPrice = OFF_PEAK_JOURNEY_PRICE;
            if (peak(journey)) {
                journeyPrice = PEAK_JOURNEY_PRICE;
            }
            customerTotal = customerTotal.add(journeyPrice);
        }

        return customerTotal;
    }

    private void manageTransaction (Customer customer, List<Journey> journeys, BigDecimal customerTotal) {
        PaymentsSystem.getInstance().charge(customer, journeys, roundToNearestPenny(customerTotal));

    }

    private BigDecimal roundToNearestPenny(BigDecimal poundsAndPence) {
        return poundsAndPence.setScale(2, BigDecimal.ROUND_HALF_UP);
    }
}
