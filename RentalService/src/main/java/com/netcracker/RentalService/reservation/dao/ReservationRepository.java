package com.netcracker.RentalService.reservation.dao;

import com.netcracker.RentalService.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, UUID> {
    List<Reservation> findByUserId(UUID userId);

    List<Reservation> findByBookId(UUID bookId);
}
