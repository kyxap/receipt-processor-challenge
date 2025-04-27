package com.receipt.processor.challenge.service;

import com.receipt.processor.challenge.model.Item;
import com.receipt.processor.challenge.model.Receipt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class ReceiptServiceTest {

    @Autowired
    private ReceiptService receiptService;

    @Test
    void testCalculatePoints_simpleCase() {
        final Receipt receipt = new Receipt();
        receipt.setRetailer("Walgreens");
        receipt.setPurchaseDate("2022-01-02");
        receipt.setPurchaseTime("08:13");
        receipt.setTotal("2.65");

        final List<Item> items = List.of(
                new Item("Pepsi - 12-oz", "1.25"),
                new Item("Dasani", "1.40")
        );
        receipt.setItems(items);

        final int points = receiptService.calculatePoints(receipt);

        assertEquals(15, points);
    }

    @Test
    void testCalculatePoints_TargetReceipt() {
        final Receipt receipt = new Receipt();
        receipt.setRetailer("Target");
        receipt.setPurchaseDate("2022-01-01");
        receipt.setPurchaseTime("13:01");
        receipt.setTotal("35.35");

        final List<Item> items = List.of(
                new Item("Mountain Dew 12PK", "6.49"),
                new Item("Emils Cheese Pizza", "12.25"),
                new Item("Knorr Creamy Chicken", "1.26"),
                new Item("Doritos Nacho Cheese", "3.35"),
                new Item("   Klarbrunn 12-PK 12 FL OZ  ", "12.00")
        );
        receipt.setItems(items);

        final int points = receiptService.calculatePoints(receipt);

        assertEquals(28, points);
    }

    @Test
    void testCalculatePoints_MAndMReceipt() {
        final Receipt receipt = new Receipt();
        receipt.setRetailer("M&M Corner Market");
        receipt.setPurchaseDate("2022-03-20");
        receipt.setPurchaseTime("14:33");
        receipt.setTotal("9.00");

        final List<Item> items = List.of(
                new Item("Gatorade", "2.25"),
                new Item("Gatorade", "2.25"),
                new Item("Gatorade", "2.25"),
                new Item("Gatorade", "2.25")
        );
        receipt.setItems(items);

        final int points = receiptService.calculatePoints(receipt);

        assertEquals(109, points);
    }
}

