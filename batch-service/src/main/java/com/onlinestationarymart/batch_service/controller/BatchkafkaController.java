package com.onlinestationarymart.batch_service.controller;

import com.onlinestationarymart.batch_service.producer.BatchOrderKafkaProducer;
import com.onlinestationarymart.domain_service.dto.OrderDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/batchEvent/placeOrder")
public class BatchkafkaController {

	@Autowired
    BatchOrderKafkaProducer batchOrderKafkaProducer;

    public ResponseEntity<String> placeOrder(OrderDTO orderDTO){
    	batchOrderKafkaProducer.placeOrderEvent(orderDTO);
        return new ResponseEntity<>("Sent Successfully!!", HttpStatus.OK);
    }
}
