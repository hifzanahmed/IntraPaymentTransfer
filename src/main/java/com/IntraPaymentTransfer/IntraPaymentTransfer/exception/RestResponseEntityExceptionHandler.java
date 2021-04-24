package com.IntraPaymentTransfer.IntraPaymentTransfer.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DebitAccountNotFoundException.class)
    protected ResponseEntity<Object> debitAccountNotFoundException(RuntimeException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN,
                ex.getMessage(), "Debit Account number not found in Account-Table");
        return new ResponseEntity<Object>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(CreditAccountNotFoundException.class)
    protected ResponseEntity<Object> creditAccountNotFoundException(RuntimeException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
                ex.getMessage(), "Invalid receiver account details");
        return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AccountNotFoundException.class)
    protected ResponseEntity<Object> accountNotFoundException(RuntimeException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN,
                ex.getMessage(), "Invalid account number");
        return new ResponseEntity<Object>(message, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(NoAccountPresentException.class)
    protected ResponseEntity<Object> noAccountPresentException(RuntimeException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND,
                ex.getMessage(), "No Account present");
        return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InsufficientFundException.class)
    protected ResponseEntity<Object> insufficientFundException(RuntimeException ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST,
                ex.getMessage(), "Insufficient fund available");
        return new ResponseEntity<Object>(message, HttpStatus.BAD_REQUEST);
    }
}
