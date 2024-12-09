package com.netcracker.RentalService.rent.service;

import com.netcracker.RentalService.rent.enumerations.Status;
import com.netcracker.RentalService.rent.entity.Rent;
import com.netcracker.RentalService.rent.exceptions.BookServiceException;
import com.netcracker.RentalService.rent.exceptions.InsufficientBalanceException;
import com.netcracker.RentalService.rent.exceptions.RentFailureException;
import com.netcracker.RentalService.rent.exceptions.UserServiceException;

import java.util.List;
import java.util.UUID;


public interface RentService {


    Rent addRentRecord(UUID userId, UUID bookId) throws RentFailureException, InsufficientBalanceException, BookServiceException, UserServiceException;
    List<Rent> getRentByUser(UUID userId);

    List<Rent> getBookReturnByUser(Status status, UUID userId);

    Rent updateRent(UUID rentId) throws BookServiceException;

    void sendExpirationNotification();
    String rateBookById(UUID bookId, int rating) throws BookServiceException;
}
