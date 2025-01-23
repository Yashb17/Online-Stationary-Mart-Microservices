package com.onlinestationarymart.inventory_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "inventory")
public class Inventory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_code" , nullable = false, unique = true)
    private String productCode;

    @Column(name = "stock_quantity" , nullable = false)
    private Integer stockQuantity;

    public Inventory(String productCode, Integer stockQuantity) {
        this.productCode = productCode;
        this.stockQuantity = stockQuantity;
    }
}
