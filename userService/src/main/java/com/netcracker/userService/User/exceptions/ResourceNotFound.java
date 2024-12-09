package com.netcracker.userService.User.exceptions;

import java.util.UUID;

public class ResourceNotFound extends RuntimeException {
    String entity;
    String field;
    UUID fieldNum;
    public ResourceNotFound() {
    }

    public ResourceNotFound(String message) {
        super(message);
    }

    public ResourceNotFound(String entity, String field, UUID fieldNum) {
        super(entity+" for "+field+" : "+fieldNum+" NotFound");
        this.entity=entity;
        this.field=field;
        this.fieldNum=fieldNum;

    }
}
