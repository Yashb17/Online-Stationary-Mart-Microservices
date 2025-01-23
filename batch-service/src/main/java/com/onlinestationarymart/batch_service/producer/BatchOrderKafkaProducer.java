package com.onlinestationarymart.batch_service.producer;

import com.onlinestationarymart.domain_service.dto.OrderDTO;
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
public class BatchOrderKafkaProducer {

	@Value("${batch.order.events.topic.name}")
	private String topic_name;

	private static final Logger LOGGER = LoggerFactory.getLogger(BatchOrderKafkaProducer.class);

	@Autowired
	private KafkaTemplate<String, OrderDTO> kafkaTemplate;

	public void placeOrderEvent(OrderDTO orderDTO) {
		LOGGER.info("@@@@ KafkaService: Entered placeOrderEvent. Sending to TopicName: {} with" + "OrderDTO; {}",
				topic_name, orderDTO.toString());

		Message<OrderDTO> message = MessageBuilder.withPayload(orderDTO)
				.setHeader(KafkaHeaders.TOPIC, topic_name)
				.build();
		kafkaTemplate.send(message);
	}
}
