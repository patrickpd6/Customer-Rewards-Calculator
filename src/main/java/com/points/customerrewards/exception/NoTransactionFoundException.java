package com.points.customerrewards.exception;

public class NoTransactionFoundException extends Exception {

    private String message;

    public NoTransactionFoundException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
