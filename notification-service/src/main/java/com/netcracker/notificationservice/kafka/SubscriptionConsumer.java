package com.netcracker.notificationservice.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netcracker.notificationservice.dto.SubscriptionEvent;
import com.netcracker.notificationservice.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
public class SubscriptionConsumer {

    private static final Logger LOGGER= LoggerFactory.getLogger(SubscriptionConsumer.class);

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    EmailService emailService;


    @KafkaListener(topics = "${spring.kafka.topic.name1}",
            groupId = "${spring.kafka.consumer.group-id1}")
    public void consume(String message) throws JsonProcessingException {

        SubscriptionEvent subscriptionEvent=objectMapper.readValue(message, SubscriptionEvent.class);

        LOGGER.info(String.format("the user message => %s",message));
        LOGGER.info(String.format("the subscription object => %s",subscriptionEvent.toString()));

        String emailSubject="Library Subscription expiring  soon!!!!";

        String emailBody="Hi "+subscriptionEvent.getFirst_name()+",\n"+
                "Your subscription is ending on "+subscriptionEvent.getSubEndDate()+
                "\n please head back to our site and renew your subscription.."+
                "\n \nRegards, \n Library";

        emailService.sendEmail(subscriptionEvent.getEmail(),emailSubject,emailBody);
    }
}
