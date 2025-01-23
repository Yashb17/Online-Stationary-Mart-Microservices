package com.onlinestationarymart.inventory_service.scheduler;

import com.onlinestationarymart.domain_service.dto.InventoryDTO;
import com.onlinestationarymart.domain_service.dto.ProductDTO;
import com.onlinestationarymart.domain_service.enums.NotificationEnum;
import com.onlinestationarymart.inventory_service.kafka.service.SendInventoryEmailService;
import com.onlinestationarymart.inventory_service.repository.InventoryRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Component
public class InventoryManagementScheduler{

    @Value("${product.Service.Base.Uri}")
    private String productServiceBaseUri;

    @Autowired
    WebClient webClient;

    @Autowired
    InventoryRepository inventoryRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    SendInventoryEmailService sendInventoryEmailService;

    @Value("${inventory.stock.quantity.threshold.value}")
    private Integer thresholdStockQuantity;

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryManagementScheduler.class);

    private static final String getAllProductsByProductCodesUri = "getAllProductsByProductCodes";

    @SuppressWarnings("unchecked")
	@Scheduled(cron = "${scheduler.cron.expression}") //"0 0 0/1 1/1 * ? *"
    public void process() {
        LOGGER.info("@@@@ Inside -> InventoryManagementScheduler");
        //take out products whose stock quantity is less than the threshold.!!
        List<InventoryDTO> listOfInventory = inventoryRepository.findAll().stream()
                .map(d -> modelMapper.map(d, InventoryDTO.class))
                .toList().stream().filter(a -> a.getStockQuantity() < thresholdStockQuantity).
                toList();
        listOfInventory.forEach(e -> {
                LOGGER.info("@@@@ Got listOfInventory: {}", e.toString());
                });
        List<Long> listOfProductCodes = listOfInventory.stream()
                .map(e -> Long.parseLong(e.getProductCode()))
                .collect(Collectors.toList());
        LOGGER.info("@@@@ Got List of Long Product Codes: {}", listOfProductCodes);


        List<ProductDTO> productsList = new ArrayList<>();
        try {
            //no inspection unchecked
            productsList = webClient.post()
                    .uri(productServiceBaseUri + getAllProductsByProductCodesUri)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(listOfProductCodes), List.class)
                    .retrieve()
                    .bodyToMono(List.class)
                    .block();
        } catch (Exception ex) {
            LOGGER.error("Error occurred while retrieving Products from ProductService ", ex);
            throw new RuntimeException(ex);
        }
        LOGGER.info("@@@@ -> {}", productsList);

        //SendEmail - Async
        LOGGER.info("@@@@ InventoryManagementScheduler: Sending Email for Order Event");
        sendEmailForInventoryRefilling(productsList, NotificationEnum.INVENTORY_REFILLING_REQUIRED.getDisplayName());
        LOGGER.info("@@@@ InventoryManagementScheduler: Sent Mail! Process Over");

        LOGGER.info("@@@@ Completed -> InventoryManagementScheduler");
    }

    private void sendEmailForInventoryRefilling(List<ProductDTO> productDTOList, String emailEventCode){
        LOGGER.info("@@@@ OrderService: Inside sendEmailOnOrderEvent");
        CompletableFuture.runAsync(
                () -> {
                    try {
                        sendInventoryEmailService.sendEmail(productDTOList, emailEventCode);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}
