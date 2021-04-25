package com.IntraPaymentTransfer.IntraPaymentTransfer.exception;

public class AccountNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2162060490547378200L;

    public AccountNotFoundException(String message) {
        super(message);
    }

}
