package com.netcracker.RentalService.rent.kafka;

import com.netcracker.RentalService.rent.dto.ReservationEvent;
import com.netcracker.RentalService.rent.dto.SubscriptionEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class ReservationProducer {

    private static final Logger LOGGER= LoggerFactory.getLogger(ReservationProducer.class);
    @Autowired
    private NewTopic returnTopic;
    @Autowired
    private NewTopic subTopic;
    @Autowired
    private KafkaTemplate<String, ReservationEvent> returnNotificationKafkaTemplate;
    @Autowired
    private KafkaTemplate<String, SubscriptionEvent> SubscriptionKafkaTemplate;
    public void sendMessage(ReservationEvent event){
        LOGGER.info(String.format("Order event ==>> %s",event.toString()));
        Message<ReservationEvent> message= MessageBuilder.withPayload(event).setHeader(KafkaHeaders.TOPIC,returnTopic.name()).build();
        returnNotificationKafkaTemplate.send("reservTopicTest3",event);
    }
    public void sendSubMessage(SubscriptionEvent event){
        LOGGER.info(String.format("Subscription event ==>> %s",event.toString()));
        Message<SubscriptionEvent> message= MessageBuilder.withPayload(event).setHeader(KafkaHeaders.TOPIC,subTopic.name()).build();
        SubscriptionKafkaTemplate.send("subTopic2",event);
    }
}
