package com.netcracker.RentalService.rent.exceptions;

public class BookServiceException extends Throwable {
    String book_service_not_responding;
    public BookServiceException(String book_service_not_responding) {
        super(book_service_not_responding);
    }

    public BookServiceException() {
    }
}
