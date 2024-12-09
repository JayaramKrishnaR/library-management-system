package com.netcracker.userService.User.exceptions;

import java.util.UUID;
import java.util.function.Supplier;

public class UserNotFoundException extends RuntimeException {
    String entity;
    String field;
    UUID fieldNum;

    public UserNotFoundException() {

    }

    public UserNotFoundException(String entity, String field, UUID fieldNum) {
        super(entity+" for "+field+" : "+fieldNum+" NOT FOUND ");
        this.entity = entity;
        this.field = field;
        this.fieldNum = fieldNum;
    }


}
