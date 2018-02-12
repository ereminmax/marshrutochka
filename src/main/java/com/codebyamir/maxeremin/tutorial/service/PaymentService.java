package com.codebyamir.maxeremin.tutorial.service;

import com.codebyamir.maxeremin.tutorial.model.Order;
import com.codebyamir.maxeremin.tutorial.model.User;

public interface PaymentService {
    public String createCustomer(User user);
    public void chargeCreditCard(Order order);
}
