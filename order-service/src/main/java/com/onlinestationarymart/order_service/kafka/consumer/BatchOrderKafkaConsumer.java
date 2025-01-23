package com.onlinestationarymart.order_service.kafka.consumer;

import com.onlinestationarymart.domain_service.dto.OrderDTO;
import com.onlinestationarymart.order_service.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class BatchOrderKafkaConsumer {
	
	@Autowired
	IOrderService orderService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchOrderKafkaConsumer.class);
			
	@KafkaListener(topics = "${batch.order.events.topic.name}", groupId = "${batch.order.events.groupId}")
    public void consumeMessageOrder(OrderDTO orderDTO){
        LOGGER.info("@@@@ KafkaConsumer: Received message : {}", orderDTO.toString());
        orderService.placeOrder(orderDTO);
    }
}
