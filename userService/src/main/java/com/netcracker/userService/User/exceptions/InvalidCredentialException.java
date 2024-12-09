package com.netcracker.userService.User.exceptions;

public class InvalidCredentialException extends RuntimeException {
    public InvalidCredentialException(String message) {
        super(message);
    }

    public InvalidCredentialException() {
    }
}
