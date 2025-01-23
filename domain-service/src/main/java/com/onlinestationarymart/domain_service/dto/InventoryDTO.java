package com.onlinestationarymart.domain_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class InventoryDTO {

    private Long id;
    private String productCode;
    private Integer stockQuantity;

    @Override
    public String toString() {
        return "InventoryDTO{" +
                "id=" + id +
                ", productCode='" + productCode + '\'' +
                ", stockQuantity=" + stockQuantity +
                '}';
    }
}
