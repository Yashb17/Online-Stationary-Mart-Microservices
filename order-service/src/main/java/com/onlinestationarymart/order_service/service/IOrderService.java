package com.onlinestationarymart.order_service.service;

import com.onlinestationarymart.domain_service.dto.OrderDTO;

public interface IOrderService {
    OrderDTO placeOrder(OrderDTO orderDTO);

    OrderDTO cancelOrder(Long orderId);
}
