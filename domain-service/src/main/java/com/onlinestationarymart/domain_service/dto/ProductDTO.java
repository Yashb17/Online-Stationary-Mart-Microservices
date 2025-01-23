package com.onlinestationarymart.domain_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO implements Comparable<ProductDTO> {
    private Long id;
    private Long productCode;
    private String productName;
    private Double amount;
    private String description;

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", productCode=" + productCode +
                ", productName='" + productName + '\'' +
                ", amount=" + amount +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    private String category;

    @Override
    public int compareTo(ProductDTO p) {
        if (this.getAmount() > p.getAmount()) {
            return 1;
        } else if (this.getAmount().equals(p.getAmount())) {
            return 0;
        } else return -1;
    }
}
