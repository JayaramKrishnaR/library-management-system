package com.netcracker.notificationservice.exceptions;

public class BookServiceNotRespondingException extends RuntimeException{
    String message;

    public BookServiceNotRespondingException(String message) {
        super(message);
        this.message = message;
    }
}
