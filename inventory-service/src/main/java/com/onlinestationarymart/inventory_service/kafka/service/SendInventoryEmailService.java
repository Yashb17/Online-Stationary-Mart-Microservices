package com.onlinestationarymart.inventory_service.kafka.service;

import com.onlinestationarymart.domain_service.dto.ProductDTO;
import com.onlinestationarymart.domain_service.dto.SendEmailDTO;
import com.onlinestationarymart.inventory_service.kafka.controller.InventoryKafkaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SendInventoryEmailService {

    @Autowired
    InventoryKafkaController inventoryKafkaController;

    @Value("${to.email.name}")
    private String toEmail;

    @Value("${shopkeeper.full.name}")
    private String shopKeeperFullName;

    private static final Logger LOGGER = LoggerFactory.getLogger(SendInventoryEmailService.class);

    public void sendEmail(List<ProductDTO> productDTOList, String emailEventCode) throws InterruptedException {
        LOGGER.info("@@@@ SendInventoryEmailService: Inside sendEmail");
        LOGGER.info("@@@@ SendInventoryEmailService: Inside sendEmail, thread going for sleep ---------->");
        Thread.sleep(10000L);
        LOGGER.info("@@@@ SendInventoryEmailService: Inside sendEmail, thread after sleep ---------->");
        SendEmailDTO sendEmailDTO = new SendEmailDTO();
        sendEmailDTO.setToEmail(toEmail);
        sendEmailDTO.setEventCode(emailEventCode);
        sendEmailDTO.setProductDTOList(productDTOList);
        sendEmailDTO.setFullName(shopKeeperFullName);

        //Call Kafka Producer
        ResponseEntity<String> result = inventoryKafkaController.sendEmail(sendEmailDTO);
        LOGGER.info("@@@@ SendInventoryEmailService: Kafka Producer Returned Message: {}", result.toString());
    }
}
