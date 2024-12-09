package com.netcracker.userService.User.exceptions;

import java.util.UUID;

public class InsufficientBalanceException extends RuntimeException {
    String message;
    String field;
    UUID userId;
    public InsufficientBalanceException(String message,String field,UUID id) {
        super(message +" for "+ field+" : "+id);
        this.message=message;
        this.field=field;
        this.userId=id;
    }
}
