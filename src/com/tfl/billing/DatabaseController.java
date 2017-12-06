package com.tfl.billing;

import com.oyster.OysterCard;
import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseController {

    public static List<Customer> getCustomers() {
        CustomerDatabase customerDatabase = CustomerDatabase.getInstance();
        return customerDatabase.getCustomers();
    }

    public static List<CustomerDecorator> getCustomerDecorators() {
        CustomerDatabase customerDatabase = CustomerDatabase.getInstance();
        List<Customer> customers = customerDatabase.getCustomers();
        List<CustomerDecorator> customerDecorators = new ArrayList<>();
        for (Customer customer : customers) {
            customerDecorators.add(new CustomerDecorator(customer));
        }
        return customerDecorators;
    }

    public static boolean isCardIdRegistered(UUID cardId) {
        return CustomerDatabase.getInstance().isRegisteredId(cardId);
    }
}
