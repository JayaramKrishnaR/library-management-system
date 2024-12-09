package com.netcracker.RentalService.rent.dto;

import com.netcracker.RentalService.reservation.entity.Reservation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationEvent {
    Reservation reservation;
}
