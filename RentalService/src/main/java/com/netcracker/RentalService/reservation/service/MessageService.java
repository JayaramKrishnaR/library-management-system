package com.netcracker.RentalService.reservation.service;

import com.netcracker.RentalService.rent.dto.ReservationEvent;
import com.netcracker.RentalService.rent.dto.SubscriptionEvent;
import com.netcracker.RentalService.rent.dto.User;
import com.netcracker.RentalService.rent.kafka.ReservationProducer;
import com.netcracker.RentalService.reservation.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
@Service
public class MessageService {
    @Autowired
    ReservationService reservationService;
    @Autowired
    ReservationProducer producer;
    public void sendReservationNotification(UUID bookId){
        List<Reservation> reservations=reservationService.getReservationByBook(bookId);
        Iterator<Reservation> it=reservations.iterator();
        ReservationEvent event=new ReservationEvent();
        while (it.hasNext()){
            Reservation reservation=it.next();
            event.setReservation(reservation);
            producer.sendMessage(event);
        }
    }

    public void sendSubscriptionNotification(User user){

        SubscriptionEvent subscriptionEvent=new SubscriptionEvent();

            subscriptionEvent.setUserId(user.getUserId());
            subscriptionEvent.setFirst_name(user.getFirst_name());
            subscriptionEvent.setEmail(user.getEmail());
            subscriptionEvent.setSubEndDate(user.getSubEndDate());
            producer.sendSubMessage(subscriptionEvent);

    }
}
