package com.onlinestationarymart.order_service.repository;

import com.onlinestationarymart.order_service.entity.OrderHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderHistoryRepository extends JpaRepository<OrderHistory, Long> {

    @Query(value = "SELECT oh FROM OrderHistory oh WHERE id=:id ORDER BY createdAt")
    List<OrderHistory> findOrderHistoryById(@Param("id") Long orderId);
}
