package com.onlinestationarymart.order_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.type.SqlTypes;

import java.sql.Timestamp;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "user_id" , nullable = false)
    private Map<String,Object> userInfo;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "order_products" , nullable = false)
    private Map<String,Integer> productsCodeList;

    @Column(name = "total_amount", nullable = false)
    private Double totalAmount;

    @Column(name = "order_status", nullable = false)
    private String orderStatus;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private Timestamp createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private Timestamp updatedAt;
}
