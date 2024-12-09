package com.netcracker.RentalService.rent.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Value("${spring.kafka.topic.name}")
    private String returnTopic;

    @Value("${spring.kafka.topic.name1}")
    private String subscriptionTopic;

    @Bean
    public NewTopic topic(){
        return TopicBuilder.name(returnTopic).build();
    }

    public NewTopic subTopic(){
        return TopicBuilder.name(subscriptionTopic).build();
    }


}
