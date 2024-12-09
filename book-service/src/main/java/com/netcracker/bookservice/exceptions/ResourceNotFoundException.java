package com.netcracker.bookservice.exceptions;

import java.util.UUID;

public class ResourceNotFoundException extends RuntimeException{
    private String resourceName;
    private String fieldName;
    private UUID resourceValue;

    public ResourceNotFoundException(String resourceName,String fieldName,UUID resourceValue){
        super(resourceName+" not found with "+fieldName+" "+resourceValue);
        this.resourceName=resourceName;
        this.resourceValue=resourceValue;
        this.fieldName=fieldName;
    }
}
