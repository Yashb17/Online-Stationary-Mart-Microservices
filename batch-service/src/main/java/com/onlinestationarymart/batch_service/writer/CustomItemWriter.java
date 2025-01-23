package com.onlinestationarymart.batch_service.writer;

import com.onlinestationarymart.batch_service.controller.BatchkafkaController;
import com.onlinestationarymart.domain_service.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.concurrent.CompletableFuture;

public class CustomItemWriter implements ItemWriter<OrderDTO>{
	
	@Autowired
	BatchkafkaController batchkafkaController;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomItemWriter.class);

	@Override
	public void write(Chunk<? extends OrderDTO> orderDTOList) throws Exception {
		LOGGER.info("@@@@ CustomItemWriter: Inside send order details through Producer");
		for (OrderDTO orderDTO : orderDTOList) {
			CompletableFuture.runAsync(() -> {
				try {
					LOGGER.info("@@@@ CustomItemWriter: Inside CustomItemWriter, thread going for sleep ---------->");
					Thread.sleep(10000L);
					LOGGER.info("@@@@ CustomItemWriter: Inside CustomItemWriter, thread after sleep ---------->");

					// Call Kafka Producer
					ResponseEntity<String> result = batchkafkaController.placeOrder(orderDTO);
					LOGGER.info("@@@@ CustomItemWriter: Kafka Producer Returned Message: {}", result.toString());
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});
		}
	}

}
