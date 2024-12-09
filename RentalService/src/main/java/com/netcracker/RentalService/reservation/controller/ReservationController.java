package com.netcracker.RentalService.reservation.controller;

import com.netcracker.RentalService.reservation.entity.Reservation;
import com.netcracker.RentalService.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin("*")
@RequestMapping("/rentalService")
public class ReservationController {
    @Autowired
    ReservationService reservationService;

    @GetMapping("/v1/reservation/{userId}")
    public ResponseEntity<List<Reservation>> getAllReservationByUser(@PathVariable UUID userId){
        return new ResponseEntity<>(reservationService.getReservationByUser(userId), HttpStatus.ACCEPTED);
    }

    @GetMapping("/v1/reservation/book/{bookId}")
    public ResponseEntity<List<Reservation>> getReservationByBook(@PathVariable UUID bookId){
        return new ResponseEntity<>(reservationService.getReservationByBook(bookId),HttpStatus.ACCEPTED);
    }

    @PostMapping("/v1/addReservation/{userId}/book/{bookId}")
    public ResponseEntity<Reservation> addReservation(@PathVariable UUID userId, @PathVariable UUID bookId){
        return new ResponseEntity<>(reservationService.addReservation(userId,bookId),HttpStatus.CREATED);
    }
}
