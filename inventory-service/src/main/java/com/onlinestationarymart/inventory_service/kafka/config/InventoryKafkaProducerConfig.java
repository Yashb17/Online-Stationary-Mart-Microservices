package com.onlinestationarymart.inventory_service.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class InventoryKafkaProducerConfig {

    @Value("${inventory.events.topic.name}")
    private String topic_name;

    @Bean
    public NewTopic newTopic(){
        return TopicBuilder.name(topic_name).build();
    }
}
