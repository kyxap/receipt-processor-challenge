package com.receipt.processor.challenge.model;

import lombok.Data;

import java.util.List;

@Data
public class Receipt {
    private String retailer;
    private String purchaseDate;
    private String purchaseTime;
    private List<Item> items;
    private String total;
}

