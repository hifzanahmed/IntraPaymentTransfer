package com.IntraPaymentTransfer.IntraPaymentTransfer.model;

public class BaseResponse {
    private String message;
    private Serverties serverty;

    public enum Serverties {
        SUCCESS, INFO, WARNING, ERROR
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Serverties getServerty() {
        return serverty;
    }

    public void setServerty(Serverties serverty) {
        this.serverty = serverty;
    }
}
