package com.netcracker.RentalService.rent.exceptions;

public class RentFailureException extends Exception {
    String message;
    public RentFailureException(String rent_failed) {
        super(rent_failed);
        this.message=rent_failed;
    }


}
