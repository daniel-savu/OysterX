package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class PaymentHandler {

    public static final List<Customer> CUSTOMERS = DatabaseController.getCustomers();
    private static final BigDecimal OFF_PEAK_CAP = new BigDecimal("7.00");
    private static final BigDecimal PEAK_CAP = new BigDecimal("9.00");
    Calculator calculator = new Calculator();


    public void chargeAccounts() {
        for (Customer customer : CUSTOMERS) {
            makePayment(customer);
        }
    }

    void makePayment (Customer customer) {

        List<JourneyEvent> customerJourneyEvents = collectCustomerJourneyEvents(customer);
        List<Journey> journeys = transformJourneyEventsToJourneys(customerJourneyEvents);
        BigDecimal customerTotal = getCustomerTotal(journeys);
        manageTransaction(customer,journeys, customerTotal);
    }

    List<JourneyEvent> collectCustomerJourneyEvents(Customer customer) {

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

    private BigDecimal getCustomerTotal(List<Journey> journeys) {
        BigDecimal customerTotal = new BigDecimal("0");
        BigDecimal priceOfJourney = null;
        boolean customer_travelled_on_peak_time = false;

        for (Journey journey : journeys) {
            priceOfJourney = calculator.calculatePriceOfJourney(journey);
            if (calculator.journeyIsPeakTime(journey)) customer_travelled_on_peak_time = true;
            try{
                customerTotal = customerTotal.add(priceOfJourney);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }

        }

        customerTotal = adaptToCap(customerTotal, customer_travelled_on_peak_time);

        return customerTotal;
    }

    private BigDecimal adaptToCap(BigDecimal customerTotal, boolean customer_travelled_on_peak_time) {
        if (customer_travelled_on_peak_time) {
            if (customerTotal.compareTo(PEAK_CAP) == 1) {
                customerTotal = PEAK_CAP;
            }
        }
        else {
            if (customerTotal.compareTo(OFF_PEAK_CAP) == 1) {
                customerTotal = OFF_PEAK_CAP;
            }
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
