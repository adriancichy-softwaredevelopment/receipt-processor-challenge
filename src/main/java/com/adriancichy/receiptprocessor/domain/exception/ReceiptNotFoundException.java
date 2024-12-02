package com.adriancichy.receiptprocessor.domain.exception;

public class ReceiptNotFoundException extends RuntimeException {

    public ReceiptNotFoundException(String message) {
        super(message);
    }

}
