package com.tfl.billing;

import java.util.List;


public class PaymentHandler {

    public final List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
    Config config = new Config();
    Utility utility = new Utility();


    public void chargeAccounts() {
        for (CustomerDecorator customerDecorator : customerDecorators) {
            customerDecorator.makePayment();
        }
    }








}
