package com.onlinestationarymart.order_service.kafka.producer;

import com.onlinestationarymart.domain_service.dto.SendEmailDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class OrderKafkaProducer {

    @Value("${order.events.topic.name}")
    private String topic_name;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderKafkaProducer.class);
    @Autowired
    private KafkaTemplate<String, SendEmailDTO> kafkaTemplate;

    public void sendEmailForOrderEvent(SendEmailDTO sendEmailDTO){
        LOGGER.info("@@@@ KafkaService: Entered sendEmailForOrderEvent. Sending to TopicName: {} with" +
                "Send Email Request; {}", topic_name, sendEmailDTO);

        Message<SendEmailDTO> message = MessageBuilder
                .withPayload(sendEmailDTO)
                .setHeader(KafkaHeaders.TOPIC, topic_name)
                .build();
        kafkaTemplate.send(message);
    }


}
