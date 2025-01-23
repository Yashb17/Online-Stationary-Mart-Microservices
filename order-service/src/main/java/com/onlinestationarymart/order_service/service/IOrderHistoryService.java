package com.onlinestationarymart.order_service.service;

import com.onlinestationarymart.domain_service.dto.OrderHistoryDTO;

import java.util.List;

public interface IOrderHistoryService {
    OrderHistoryDTO createNewEntry(OrderHistoryDTO orderHistoryDTO);

    List<OrderHistoryDTO> getTrackingInfoForOrderId(Long orderid);
}
