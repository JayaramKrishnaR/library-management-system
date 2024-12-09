package com.netcracker.RentalService.rent.exceptions;

public class InsufficientBalanceException extends Throwable {
    public InsufficientBalanceException() {
    }

    public InsufficientBalanceException(String message) {
        super(message);
    }
    public String getMessage(String message) {
        return message;
    }
}
