package com.netcracker.RentalService.reservation.service;

import com.netcracker.RentalService.reservation.dao.ReservationRepository;
import com.netcracker.RentalService.reservation.dto.ReservationDto;
import com.netcracker.RentalService.reservation.entity.Reservation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReservationServiceImpl implements ReservationService{
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    ModelMapper mapper;
    @Override
    public List<Reservation> getReservationByUser(UUID userId) {

        return reservationRepository.findByUserId(userId);
    }

    @Override
    public Reservation addReservation(UUID userId, UUID bookId) {
        ReservationDto reservationDto=new ReservationDto(null,userId,bookId);
        return reservationRepository.save(mapper.map(reservationDto,Reservation.class));
    }

    @Override
    public List<Reservation> getReservationByBook(UUID bookId) {
        return reservationRepository.findByBookId(bookId);
    }
}
