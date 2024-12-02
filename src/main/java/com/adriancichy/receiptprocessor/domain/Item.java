package com.adriancichy.receiptprocessor.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Item {

    private String shortDescription;

    private Float price;

}
