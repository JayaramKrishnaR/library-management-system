package com.netcracker.RentalService.reservation.service;

import com.netcracker.RentalService.reservation.entity.Reservation;
import java.util.List;
import java.util.UUID;

public interface ReservationService {
    List<Reservation> getReservationByUser(UUID userId);
    Reservation addReservation(UUID userId,UUID bookId);

    List<Reservation> getReservationByBook(UUID bookId);
}
