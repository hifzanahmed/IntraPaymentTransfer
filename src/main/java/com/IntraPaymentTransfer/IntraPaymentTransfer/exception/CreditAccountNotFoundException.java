package com.IntraPaymentTransfer.IntraPaymentTransfer.exception;

public class CreditAccountNotFoundException extends RuntimeException {


    private static final long serialVersionUID = -6661339410857780437L;

    public CreditAccountNotFoundException(String message) {
        super(message);
    }

}
