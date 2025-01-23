package com.onlinestationarymart.order_service.service.impl;

import com.onlinestationarymart.domain_service.Exception.ResourceNotFoundException;
import com.onlinestationarymart.domain_service.dto.OrderDTO;
import com.onlinestationarymart.domain_service.dto.OrderHistoryDTO;
import com.onlinestationarymart.domain_service.dto.ResponseDTO;
import com.onlinestationarymart.domain_service.enums.OrderStatusEnum;
import com.onlinestationarymart.order_service.entity.Order;
import com.onlinestationarymart.order_service.kafka.service.SendEmailService;
import com.onlinestationarymart.order_service.repository.OrderRepository;
import com.onlinestationarymart.order_service.service.IOrderService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService implements IOrderService {

    @Value("${inventory.Service.Base.Uri}")
    private String inventoryServiceBaseUri;

    @Value("${product.Service.Base.Uri}")
    private String productServiceBaseUri;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    OrderHistoryService orderHistoryService;

    @Autowired
    WebClient webClient;

    @Autowired
    SendEmailService sendEmailService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderService.class);
    private static final String checkAndUpdateInventoryStatus = "checkAndUpdateInventoryStatus";
    private static final String getTotalAmountForProducts = "getTotalAmountForProducts";

    @Override
    @Transactional
    public OrderDTO placeOrder(OrderDTO orderDTO) {
        LOGGER.info("@@@@ OrderService: Inside placeOrder  method for creating a new Order with data-> {}", orderDTO);

        //check inventory and update it accordingly
        LOGGER.info("@@@@ OrderService: External call->Inventory Service for inventoryUpdate: {}", orderDTO.getProductsCodeList());
        Boolean isInventoryCheckOkAndUpdated = Boolean.FALSE;
        ResponseDTO responseDTO = null;
        try {
            responseDTO = webClient.post()
                    .uri(inventoryServiceBaseUri + checkAndUpdateInventoryStatus)
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(orderDTO.getProductsCodeList()), Map.class)
                    .retrieve()
                    .bodyToMono(ResponseDTO.class)
                    .block();
        } catch (Exception ex) {
            LOGGER.error("Error occurred while updating Inventory ", ex);
            throw new RuntimeException(ex);
        }
        isInventoryCheckOkAndUpdated = Objects.nonNull(responseDTO) ? responseDTO.getSuccess() : Boolean.FALSE;
        if(Boolean.TRUE.equals(isInventoryCheckOkAndUpdated)){
            return createOrderEntry(orderDTO);
        } else{
            LOGGER.error("Order has Products which are out of stock: {}", orderDTO);
            return orderDTO;
        }
    }

    @Override
    @Transactional
    public OrderDTO cancelOrder(Long orderId) {
        LOGGER.info("@@@@ OrderService: Entered Cancel Order" );
        Order orderDB = orderRepository.findById(orderId).orElseThrow(
                () -> new ResourceNotFoundException("order", "id", orderId)
        );
        //update order history table
        OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO(orderDB.getOrderId(), OrderStatusEnum.ORDER_CANCELLED.getDisplayName());
        OrderHistoryDTO savedOrderHistoryDTO = orderHistoryService.createNewEntry(orderHistoryDTO);
        LOGGER.info("@@@@ OrderService: Saved OrderHistoryDTO: {}", savedOrderHistoryDTO);

        //update Order table
        orderDB.setOrderStatus(OrderStatusEnum.ORDER_CANCELLED.getDisplayName());
        Order updatedOrderDB = orderRepository.save(orderDB);
        LOGGER.info("@@@@ OrderService: After Cancel Order updatedOrder  in DB: {}", updatedOrderDB);

        return modelMapper.map(updatedOrderDB, OrderDTO.class);
    }

    @Transactional
    private OrderDTO createOrderEntry(OrderDTO orderDTO) {
        LOGGER.info("@@@@ OrderService: Entered Placing Order in Order tables with data: {}", orderDTO);

        //Get total amount
        double totalAmount = 0D;
        try {
            totalAmount = Optional.ofNullable(webClient.post()
                    .uri(productServiceBaseUri + getTotalAmountForProducts)
                    .accept(MediaType.APPLICATION_JSON)
                    .body(Mono.just(orderDTO.getProductsCodeList()), Map.class)
                    .retrieve()
                    .bodyToMono(Double.class)
                    .block()).orElseThrow(NullPointerException::new);
        } catch (Exception ex) {
            LOGGER.error("Error occurred while updating Inventory ", ex);
            throw new RuntimeException(ex);
        }
        orderDTO.setTotalAmount(totalAmount);

        //place order
        orderDTO.setOrderStatus(OrderStatusEnum.ORDER_RECEIVED.getDisplayName());
        Order order = modelMapper.map(orderDTO, Order.class);
        Order createdOrder= orderRepository.save(order);
        OrderDTO createdOrderDTO = modelMapper.map(createdOrder, OrderDTO.class);
        LOGGER.info("@@@@ OrderService: Created Order -> {}", createdOrderDTO);

        //update order history table
        OrderHistoryDTO orderHistoryDTO = new OrderHistoryDTO(order.getOrderId(), OrderStatusEnum.ORDER_RECEIVED.getDisplayName());
        OrderHistoryDTO savedOrderHistoryDTO = orderHistoryService.createNewEntry(orderHistoryDTO);
        LOGGER.info("@@@@ OrderService: Saved OrderHistoryDTO -> {} ", savedOrderHistoryDTO);

        //SendEmail - Async
        LOGGER.info("@@@@ OrderService: Sending Email for Order Event");
        sendEmailOnOrderEvent(createdOrderDTO, OrderStatusEnum.ORDER_RECEIVED.getDisplayName());
        LOGGER.info("@@@@ OrderService: Sent Mail! Process Over");
        return createdOrderDTO;
    }

    private void sendEmailOnOrderEvent(OrderDTO createdOrderDTO, String emailEventCode){
        LOGGER.info("@@@@ OrderService: Inside sendEmailOnOrderEvent");
        CompletableFuture.runAsync(
                () -> {
                    try {
                        sendEmailService.sendEmail(createdOrderDTO, emailEventCode);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
        );
    }
}
