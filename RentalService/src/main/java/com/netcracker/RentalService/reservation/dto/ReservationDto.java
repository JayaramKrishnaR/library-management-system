package com.netcracker.RentalService.reservation.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ReservationDto {
    UUID reservationId;
    UUID userId;
    UUID bookId;
}
