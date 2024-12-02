package com.adriancichy.receiptprocessor.domain;

import com.adriancichy.receiptprocessor.domain.exception.ReceiptNotFoundException;

import java.util.UUID;

public interface ReceiptRepository {

    UUID create(Receipt receipt);

    Receipt findById(UUID id) throws ReceiptNotFoundException;

}
