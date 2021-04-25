package com.IntraPaymentTransfer.IntraPaymentTransfer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class DebitAccountNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 2441536754543148403L;

    public DebitAccountNotFoundException(String message) {
        super(message);
    }

}
