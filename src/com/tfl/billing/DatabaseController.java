package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.CustomerDatabase;

import java.util.List;
import java.util.UUID;

public class DatabaseController {

    public static List<Customer> getCustomers() {
        CustomerDatabase customerDatabase = CustomerDatabase.getInstance();
        return customerDatabase.getCustomers();
    }

    public static boolean isCardIdRegistered(UUID cardId) {
        return CustomerDatabase.getInstance().isRegisteredId(cardId);
    }

}
