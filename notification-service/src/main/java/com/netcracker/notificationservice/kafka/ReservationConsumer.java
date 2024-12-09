package com.netcracker.notificationservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.notificationservice.client.BookClient;
import com.netcracker.notificationservice.client.UserClient;
import com.netcracker.notificationservice.dto.*;
import com.netcracker.notificationservice.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class ReservationConsumer {

    private static final Logger LOGGER= LoggerFactory.getLogger(ReservationConsumer.class);

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EmailService emailService;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    UserClient userClient;

    @Autowired
    BookClient bookClient;


    @KafkaListener(topics = "${spring.kafka.topic.name}",
            groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String message) throws JsonProcessingException {

        String message1=message.substring(message.indexOf(":")+1);

        Reservation reservation=objectMapper.readValue(message1, Reservation.class);

        LOGGER.info(String.format("the reservation record => %s",message));
        LOGGER.info(String.format("the reservation object => %s",reservation.toString()));

        UserDto userDto= userClient.getUserById(reservation.getUserId());

        BookDto bookDto= bookClient.getBookById(reservation.getBookId());

        String emailSubject="Book "+bookDto.getBookTitle()+" is back in stock";

        String emailBody="Hi "+userDto.getFirst_name()+","+
                "\n The book you had reserved"+"\n Title: "+bookDto.getBookTitle()+
                "\n Author: "+bookDto.getBookAuthor()+
                "\n Publication: "+bookDto.getPublication()+
                "\n is back in library., \n Rent it quickly so you don't miss it"+
                "\n \n Regards, \n Library";

        emailService.sendEmail(userDto.getEmail(),emailSubject,emailBody);


    }











}
