package com.netcracker.bookservice.exceptions;

public class CopiesNotSufficientException extends RuntimeException{
    private String message;
    private boolean status;

    public CopiesNotSufficientException(String message, boolean status){
        super(message);
        this.status=status;
        this.message=message;
    }
}
