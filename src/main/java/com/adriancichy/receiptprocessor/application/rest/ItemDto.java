package com.adriancichy.receiptprocessor.application.rest;

import com.adriancichy.receiptprocessor.domain.Item;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.NotNull;

record ItemDto(
        
    @NotNull
    @Pattern(regexp = "^[\\w\\s\\-]+$", message = "Short description must contain only letters, numbers, spaces, and hyphens")
    String shortDescription,

    @NotNull
    @Pattern(regexp = "^\\d+\\.\\d{2}$", message = "Price must be a number with exactly two decimal places (e.g., '6.49')")
    String price
    
) {

    public static Item toDomain(ItemDto dto) {
        return new Item(
                dto.shortDescription(),
                Float.parseFloat(dto.price())
        );
    }
    
}
