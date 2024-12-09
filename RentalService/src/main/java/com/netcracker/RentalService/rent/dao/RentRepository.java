package com.netcracker.RentalService.rent.dao;

import com.netcracker.RentalService.rent.enumerations.Status;
import com.netcracker.RentalService.rent.entity.Rent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface RentRepository extends JpaRepository<Rent,UUID> {

    @Override
    Optional<Rent> findById(UUID integer);

    List<Rent> findByUserId(UUID userId);
    @Query("select r from Rent r where r.status = ?1 and r.userId = ?2")
    List<Rent> findByStatus(Status status, UUID userId);
}
