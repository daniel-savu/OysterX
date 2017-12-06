package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DatabaseController {

    private DatabaseController() {

    }

    public static List<CustomerDecorator> getCustomerDecorators() {
        List<Customer> customers = getCustomers();
        List<CustomerDecorator> customerDecorators = new ArrayList<>();
        for (Customer customer : customers) {
            customerDecorators.add(new CustomerDecorator(customer));
        }
        return customerDecorators;
    }

    public static List<Customer> getCustomers() {
        CustomerDatabase customerDatabase = CustomerDatabase.getInstance();
        return customerDatabase.getCustomers();
    }

    public static boolean isCardIdRegistered(UUID cardId) {
        return CustomerDatabase.getInstance().isRegisteredId(cardId);
    }
}
