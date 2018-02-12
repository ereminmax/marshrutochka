package com.codebyamir.maxeremin.tutorial.model;

import lombok.Data;

@Data
public class Order {
    private User user;
    private double chargeAmountDollars;
}
