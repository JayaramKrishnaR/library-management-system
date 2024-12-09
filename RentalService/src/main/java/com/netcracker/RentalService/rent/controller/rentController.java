package com.netcracker.RentalService.rent.controller;

import com.netcracker.RentalService.rent.clients.BookClient;
import com.netcracker.RentalService.rent.enumerations.Status;
import com.netcracker.RentalService.rent.entity.Rent;
import com.netcracker.RentalService.rent.exceptions.BookServiceException;
import com.netcracker.RentalService.rent.exceptions.InsufficientBalanceException;
import com.netcracker.RentalService.rent.exceptions.RentFailureException;
import com.netcracker.RentalService.rent.exceptions.UserServiceException;
import com.netcracker.RentalService.rent.service.*;
import com.netcracker.RentalService.reservation.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin("*")
@RequestMapping("/rentalService")
public class rentController {
    @Autowired
    RentService rentService;

    @Autowired
    MessageService messageService;
    @Autowired
    BookClient bookClient;

    @PostMapping(value = "/v1/user/{userId}/rent/{bookId}")
    public ResponseEntity<Rent> addRent(@PathVariable("userId") UUID userId, @PathVariable UUID bookId) throws RentFailureException, InsufficientBalanceException, UserServiceException, BookServiceException {
        return new ResponseEntity<>(rentService.addRentRecord(userId,bookId), HttpStatus.CREATED);
    }
    @GetMapping(value = "/v1/rent/{userId}")
    public ResponseEntity<List<Rent>> getBooksByUser(@PathVariable UUID userId){
        return new ResponseEntity<>(rentService.getBookReturnByUser(Status.ISSUED,userId),HttpStatus.OK);
    }

    @PutMapping(value = "/v1/rent/{issueId}")
    public ResponseEntity<Rent> updateRentOfUser(@PathVariable UUID issueId) throws BookServiceException {

        Rent rent = rentService.updateRent(issueId);
        messageService.sendReservationNotification(rent.getBook_id());
        return new ResponseEntity<>(rent,HttpStatus.CREATED);
    }

    @GetMapping(value = "/v1/rent/return/{userId}")
    public ResponseEntity<List<Rent>> getBooksReturnByUser(@PathVariable UUID userId){
        return new ResponseEntity<>(rentService.getBookReturnByUser(Status.RETURNED,userId),HttpStatus.OK);
    }
    @PostMapping(value = "/v1/return/rate")
    public ResponseEntity<String> rateBook(@RequestParam("bookId") UUID bookId,@RequestParam("rating") int rating ) throws BookServiceException {
        return new ResponseEntity<>(rentService.rateBookById(bookId,rating),HttpStatus.OK);
    }
}
