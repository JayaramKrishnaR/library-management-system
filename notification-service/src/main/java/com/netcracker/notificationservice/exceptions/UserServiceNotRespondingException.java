package com.netcracker.notificationservice.exceptions;

public class UserServiceNotRespondingException extends RuntimeException{
    String message;

    public UserServiceNotRespondingException(String message) {
        super(message);
        this.message = message;
    }
}
