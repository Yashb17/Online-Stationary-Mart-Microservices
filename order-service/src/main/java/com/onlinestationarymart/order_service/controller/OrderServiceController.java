package com.onlinestationarymart.order_service.controller;

import com.onlinestationarymart.domain_service.dto.OrderDTO;
import com.onlinestationarymart.order_service.service.IOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/order")
public class OrderServiceController {

    @Autowired
    private IOrderService orderService;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceController.class);

    //Post Request - placeOrder  http://localhost:8081/api/order/placeOrder
    @PostMapping("placeOrder")
    public ResponseEntity<OrderDTO> addProduct(@RequestBody OrderDTO orderDTO){
        LOGGER.info("@@@@ OrderController: Received an Order: {}", orderDTO);
        OrderDTO savedOrderDTO = orderService.placeOrder(orderDTO);
        return new ResponseEntity<>(savedOrderDTO, HttpStatus.CREATED);
    }

    //Delete Request - placeOrder  http://localhost:8081/api/product/placeOrder
    @DeleteMapping("cancelOrder")
    public ResponseEntity<OrderDTO> cancelOrder(@RequestBody Long orderId){
        LOGGER.info("@@@@ OrderController: Received an Cancellation Request for OrderId: {}", orderId);
        OrderDTO updatedOrderDTO = orderService.cancelOrder(orderId);
        return new ResponseEntity<>(updatedOrderDTO, HttpStatus.OK);
    }


}
