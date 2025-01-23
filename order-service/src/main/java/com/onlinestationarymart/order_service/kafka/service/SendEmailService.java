package com.onlinestationarymart.order_service.kafka.service;

import com.onlinestationarymart.domain_service.dto.OrderDTO;
import com.onlinestationarymart.domain_service.dto.SendEmailDTO;
import com.onlinestationarymart.order_service.kafka.controller.OrderKafkaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SendEmailService {

    @Autowired
    OrderKafkaController orderKafkaController;

    private static final Logger LOGGER = LoggerFactory.getLogger(SendEmailService.class);

    public void sendEmail(OrderDTO orderDTO, String emailEventCode) throws InterruptedException {
        LOGGER.info("@@@@ SendEmailService: Inside sendEmail");
        LOGGER.info("@@@@ SendEmailService: Inside sendEmail, thread going for sleep ---------->");
        Thread.sleep(10000L);
        LOGGER.info("@@@@ SendEmailService: Inside sendEmail, thread after sleep ---------->");
        SendEmailDTO sendEmailDTO = new SendEmailDTO();
        sendEmailDTO.setToEmail(orderDTO.getUserInfo().get("email").toString());
        sendEmailDTO.setEventCode(emailEventCode);
        sendEmailDTO.setProductsCodeMap(orderDTO.getProductsCodeList());
        sendEmailDTO.setTotalAmount(orderDTO.getTotalAmount());
        sendEmailDTO.setFullName(orderDTO.getUserInfo().get("fullName").toString());
        sendEmailDTO.setOrderId(orderDTO.getOrderId());

        //Call Kafka Producer
        ResponseEntity<String> result = orderKafkaController.sendEmail(sendEmailDTO);
        LOGGER.info("@@@@ SendEmailService: Kafka Producer Returned Message: {}", result.toString());
    }
}
