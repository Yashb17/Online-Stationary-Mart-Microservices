package com.onlinestationarymart.inventory_service.kafka.controller;

import com.onlinestationarymart.domain_service.dto.SendEmailDTO;
import com.onlinestationarymart.inventory_service.kafka.producer.InventoryKafkaProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orderEvent/sendEmail/")
public class InventoryKafkaController {

    @Autowired
    InventoryKafkaProducer inventoryKafkaProducer;

    public ResponseEntity<String> sendEmail(SendEmailDTO sendEmailDTO){
        inventoryKafkaProducer.sendEmailForInventoryEvent(sendEmailDTO);
        return new ResponseEntity<>("Sent Successfully!!", HttpStatus.OK);
    }
}
