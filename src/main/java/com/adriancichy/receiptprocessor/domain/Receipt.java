package com.adriancichy.receiptprocessor.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Receipt {

    private String retailer;

    private LocalDate purchaseDate;

    private LocalTime purchaseTime;

    private List<Item> items;

    private float total;

    public int calculatePoints() {
        int points = 0;

        // Rule 1: One point for every alphanumeric character in the retailer name
        points += retailer.replaceAll("[^a-zA-Z0-9]", "").length();

        // Rule 2: 50 points if the total is a round dollar amount with no cents
        if (total == Math.floor(total)) {
            points += 50;
        }

        // Rule 3: 25 points if the total is a multiple of 0.25
        if (total % 0.25 == 0) {
            points += 25;
        }

        // Rule 4: 5 points for every two items
        points += (items.size() / 2) * 5;

        // Rule 5: Item description length is a multiple of 3
        points += items.stream()
                .filter(item -> item.getShortDescription().trim().length() % 3 == 0)
                .mapToInt(item -> (int) Math.ceil(item.getPrice() * 0.2))
                .sum();

        // Rule 6: 6 points if the day in the purchase date is odd
        if (purchaseDate.getDayOfMonth() % 2 != 0) {
            points += 6;
        }

        // Rule 7: 10 points if the time of purchase is between 2:00pm and 4:00pm
        if (purchaseTime.isAfter(LocalTime.of(14, 0)) && purchaseTime.isBefore(LocalTime.of(16, 0))) {
            points += 10;
        }

        return points;
    }

}
