package com.tfl.billing;

import com.tfl.external.Customer;
import com.tfl.external.PaymentsSystem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


public class PaymentHandler {

    public final List<CustomerDecorator> customerDecorators = DatabaseController.getCustomerDecorators();
    Config config = new Config();
    Calculator calculator = new Calculator();


    public void chargeAccounts() {
        for (CustomerDecorator customerDecorator : customerDecorators) {
            customerDecorator.makePayment();
        }
    }








}
