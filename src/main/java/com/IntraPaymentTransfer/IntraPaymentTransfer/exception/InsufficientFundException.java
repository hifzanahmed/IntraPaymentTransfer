package com.IntraPaymentTransfer.IntraPaymentTransfer.exception;

public class InsufficientFundException extends RuntimeException {
    private static final long serialVersionUID = 7375900028560450865L;

    public InsufficientFundException() {
        super();
    }
    public InsufficientFundException(String message, Throwable cause) {
        super(message, cause);
    }
    public InsufficientFundException(String message) {
        super(message);
    }
    public InsufficientFundException(Throwable cause) {
        super(cause);
    }
}
