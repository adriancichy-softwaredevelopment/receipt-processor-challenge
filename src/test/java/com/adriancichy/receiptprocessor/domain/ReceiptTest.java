package com.adriancichy.receiptprocessor.domain;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReceiptTest {

    @Test
    void testExampleOne() {
        List<Item> items = List.of(
                new Item("Mountain Dew 12PK", 6.49f),
                new Item("Emils Cheese Pizza", 12.25f),
                new Item("Knorr Creamy Chicken", 1.26f),
                new Item("Doritos Nacho Cheese", 3.35f),
                new Item("   Klarbrunn 12-PK 12 FL OZ  ", 12.00f)
        );

        Receipt receipt = new Receipt(
                "Target",
                LocalDate.of(2022, 1, 1),
                LocalTime.of(13, 1),
                items,
                35.35f
        );

        int points = receipt.calculatePoints();
        assertEquals(28, points);
    }

    @Test
    void testExampleTwo() {
        List<Item> items = List.of(
                new Item("Gatorade", 2.25f),
                new Item("Gatorade", 2.25f),
                new Item("Gatorade", 2.25f),
                new Item("Gatorade", 2.25f)
        );

        Receipt receipt = new Receipt(
                "M&M Corner Market",
                LocalDate.of(2022, 3, 20),
                LocalTime.of(14, 33),
                items,
                9.00f
        );

        int points = receipt.calculatePoints();
        assertEquals(109, points);
    }

}
