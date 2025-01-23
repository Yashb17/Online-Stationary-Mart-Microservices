package com.onlinestationarymart.email_service.kafka.consumer;

import com.onlinestationarymart.domain_service.dto.SendEmailDTO;
import com.onlinestationarymart.email_service.config.EmailServiceFactory;
import com.onlinestationarymart.email_service.kafka.service.ISendEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class KafkaOrderConsumer {

    @Autowired
    EmailServiceFactory emailServiceFactory;

    @Autowired
    private ApplicationContext applicationContext;

    private static final String EmailServicesBeanMap = "emailServicesBeanMap";

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaOrderConsumer.class);

    @SuppressWarnings("unchecked")
	@KafkaListener(topics = "${order.events.topic.name}", groupId = "${order.events.groupId}")
    public void consumeMessageOrder(SendEmailDTO sendEmailDTO){
        LOGGER.info("@@@@ KafkaConsumer: Received message : {}", sendEmailDTO.toString());
        Map<String, Object> beanMap = (Map<String, Object>) applicationContext.getBean(EmailServicesBeanMap);
        ISendEmail sendEmail = (ISendEmail) beanMap.get(sendEmailDTO.getEventCode());
        sendEmail.sendEmail(sendEmailDTO);
    }

    @SuppressWarnings("unchecked")
	@KafkaListener(topics = "${inventory.events.topic.name}", groupId = "${inventory.events.groupId}")
    public void consumeMessageInventory(SendEmailDTO sendEmailDTO){
        LOGGER.info("@@@@ KafkaConsumer: Received message : {}", sendEmailDTO);
        Map<String, Object> beanMap = (Map<String, Object>) applicationContext.getBean(EmailServicesBeanMap);
        ISendEmail sendEmail = (ISendEmail) beanMap.get(sendEmailDTO.getEventCode());
        sendEmail.sendEmail(sendEmailDTO);
    }
}
