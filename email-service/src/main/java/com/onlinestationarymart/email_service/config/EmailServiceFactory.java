package com.onlinestationarymart.email_service.config;

import com.onlinestationarymart.domain_service.enums.NotificationEnum;
import com.onlinestationarymart.email_service.kafka.service.SendEmailInventoryRefill;
import com.onlinestationarymart.email_service.kafka.service.SendEmailOrderCancelled;
import com.onlinestationarymart.email_service.kafka.service.SendEmailOrderDelivered;
import com.onlinestationarymart.email_service.kafka.service.SendEmailOrderReceived;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class EmailServiceFactory {

    @Qualifier(SendEmailOrderReceived.NAME)
    @Autowired
    SendEmailOrderReceived sendEmailOrderReceived;

    @Autowired
    SendEmailOrderCancelled sendEmailOrderCancelled;

    @Autowired
    SendEmailInventoryRefill sendEmailInventoryRefill;

    @Qualifier(SendEmailOrderDelivered.NAME)
    @Autowired
    SendEmailOrderDelivered sendEmailOrderDelivered;
    private static final String EmailServicesBeanMap = "emailServicesBeanMap";

    private static final Logger LOGGER = LoggerFactory.getLogger(EmailServiceFactory.class);

    @Bean(EmailServicesBeanMap)
    public Map<String, Object> emailServicesMap() {
        Map<String, Object> emailServicesMap = new HashMap<>();
        emailServicesMap.put(NotificationEnum.ORDER_RECEIVED.getDisplayName(), sendEmailOrderReceived);
        emailServicesMap.put(NotificationEnum.ORDER_DELIVERED.getDisplayName(), sendEmailOrderDelivered);
        emailServicesMap.put(NotificationEnum.ORDER_CANCELLED.getDisplayName(), sendEmailOrderCancelled);
        emailServicesMap.put(NotificationEnum.INVENTORY_REFILLING_REQUIRED.getDisplayName(), sendEmailInventoryRefill);
        LOGGER.info("@@@@ EmailServiceFactory: Built bean Map for Email services: {}", emailServicesMap);
        return emailServicesMap;
    }
}
