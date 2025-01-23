package com.onlinestationarymart.order_service.kafka.controller;

import com.onlinestationarymart.domain_service.dto.SendEmailDTO;
import com.onlinestationarymart.order_service.kafka.producer.OrderKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderEvent/sendEmail/")
public class OrderKafkaController {

    @Autowired
    OrderKafkaProducer orderKafkaProducer;

    public ResponseEntity<String> sendEmail(SendEmailDTO sendEmailDTO){
        orderKafkaProducer.sendEmailForOrderEvent(sendEmailDTO);
        return new ResponseEntity<>("Sent Successfully!!", HttpStatus.OK);
    }
}
