package com.IntraPaymentTransfer.IntraPaymentTransfer.exception;

public class NoAccountPresentException extends RuntimeException {
    private static final long serialVersionUID = 435208403250545595L;

    public NoAccountPresentException() {
        super();
    }
    public NoAccountPresentException(String message, Throwable cause) {
        super(message, cause);
    }
    public NoAccountPresentException(String message) {
        super(message);
    }
    public NoAccountPresentException(Throwable cause) {
        super(cause);
    }
}
