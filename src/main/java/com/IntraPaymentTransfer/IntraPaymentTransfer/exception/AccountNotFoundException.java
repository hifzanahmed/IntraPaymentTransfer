package com.IntraPaymentTransfer.IntraPaymentTransfer.exception;

public class AccountNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 2162060490547378200L;

    public AccountNotFoundException() {
        super();
    }
    public AccountNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
    public AccountNotFoundException(String message) {
        super(message);
    }
    public AccountNotFoundException(Throwable cause) {
        super(cause);
    }
}
