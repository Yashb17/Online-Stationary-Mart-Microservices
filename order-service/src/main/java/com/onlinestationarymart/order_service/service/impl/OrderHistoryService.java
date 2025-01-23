package com.onlinestationarymart.order_service.service.impl;

import com.onlinestationarymart.domain_service.dto.OrderHistoryDTO;
import com.onlinestationarymart.order_service.entity.OrderHistory;
import com.onlinestationarymart.order_service.repository.OrderHistoryRepository;
import com.onlinestationarymart.order_service.service.IOrderHistoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderHistoryService implements IOrderHistoryService {

    @Autowired
    OrderHistoryRepository orderHistoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderHistoryService.class);

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public OrderHistoryDTO createNewEntry(OrderHistoryDTO orderHistoryDTO) {
        LOGGER.info("@@@@ OrderHistoryService: Received Save Request for OrderHistoryDTO -> {} ", orderHistoryDTO);
        OrderHistory orderHistory = modelMapper.map(orderHistoryDTO, OrderHistory.class);
        OrderHistory savedOrderHistory = orderHistoryRepository.save(orderHistory);
        OrderHistoryDTO savedOrderHistoryDTO = new OrderHistoryDTO(savedOrderHistory.getId(), savedOrderHistory.getOrderStatus());
        LOGGER.info("@@@@ OrderHistoryService: Saved OrderHistoryDTO -> {} ", savedOrderHistoryDTO);
        return savedOrderHistoryDTO;
    }

    @Override
    public List<OrderHistoryDTO> getTrackingInfoForOrderId(Long orderId) {
        LOGGER.info("@@@@ OrderHistoryService: Received Tracking Request for OrderId -> {} ", orderId);
        List<OrderHistory> savedOrderHistoryList = orderHistoryRepository.findOrderHistoryById(orderId);
        List<OrderHistoryDTO> savedOrderHistoryDTOList = savedOrderHistoryList.stream().map(e -> modelMapper.map(e, OrderHistoryDTO.class)).toList();
        LOGGER.info("@@@@ OrderHistoryService: Saved OrderHistoryDTO -> {} ", savedOrderHistoryDTOList);
        return savedOrderHistoryDTOList;
    }
}
