package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class PaymentHandler {

    public static final List<Customer> customers = DatabaseController.getCustomers();
    ConfigParser configParser = new ConfigParser();
    Calculator calculator = new Calculator();


    public void chargeAccounts() {
        for (Customer customer : customers) {
            makePayment(customer);
        }
    }

    void makePayment (Customer customer) {
        List<JourneyEvent> customerJourneyEvents = collectCustomerJourneyEvents(customer);
        List<Journey> journeys = transformJourneyEventsToJourneys(customerJourneyEvents);
        BigDecimal customerTotal = getCustomerTotal(journeys);
        manageTransaction(customer, journeys, customerTotal);
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
        boolean customerTravelledOnPeakTime = false;

        for (Journey journey : journeys) {
            priceOfJourney = calculator.calculatePriceOfJourney(journey);
            customerTravelledOnPeakTime = calculator.journeyIsPeakTime(journey);

            try{
                customerTotal = customerTotal.add(priceOfJourney);
            } catch(Exception e) {
                System.out.println(e.getMessage());
            }
        }

        customerTotal = applyCapIfNeeded(customerTotal, customerTravelledOnPeakTime);
        return customerTotal;
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
        if (BigDecimalCompare.greaterThan(customerTotal, configParser.getPeakCap())) {
            customerTotal = configParser.getPeakCap();
        }
        return customerTotal;
    }

    private BigDecimal applyOffPeakCapIfNeeded(BigDecimal customerTotal) {
        if (BigDecimalCompare.greaterThan(customerTotal, configParser.getOffPeakCap())) {
            customerTotal = configParser.getOffPeakCap();
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
