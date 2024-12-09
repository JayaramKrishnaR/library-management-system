package com.netcracker.RentalService.reservation.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "Reservation")
@NoArgsConstructor
@Data
public class Reservation {
    @Id
    @GeneratedValue
    @Type(type = "pg-uuid") @Column(length = 36)
    UUID reservationId;
    UUID userId;
    UUID bookId;
}
