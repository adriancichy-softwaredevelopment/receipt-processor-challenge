package com.adriancichy.receiptprocessor.application.rest;

import com.adriancichy.receiptprocessor.domain.Item;
import com.adriancichy.receiptprocessor.domain.Receipt;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

record ReceiptDto(

    @NotNull
    @Pattern(regexp = "^[\\w\\s\\-&]+$", message = "Invalid retailer name. Only letters, numbers, spaces, hyphens, and ampersands are allowed")
    String retailer,

    @NotNull
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Purchase date must be in the format yyyy-MM-dd")
    String purchaseDate,

    @NotNull
    @Pattern(regexp = "^\\d{2}:\\d{2}$", message = "Purchase time must be in the format HH:mm")
    String purchaseTime,

    @NotNull
    @Size(min = 1, message = "The receipt must contain at least one item")
    List<ItemDto> items,

    @NotNull
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Total must be a number with exactly two decimal places (e.g., '6.49')")
    String total

) {

    public static Receipt toDomain(ReceiptDto dto) {
        List<Item> items = dto.items.stream()
                .map(ItemDto::toDomain)
                .toList();

        return new Receipt(
                dto.retailer,
                LocalDate.parse(dto.purchaseDate),
                LocalTime.parse(dto.purchaseTime),
                items,
                Float.parseFloat(dto.total)
        );
    }

}
