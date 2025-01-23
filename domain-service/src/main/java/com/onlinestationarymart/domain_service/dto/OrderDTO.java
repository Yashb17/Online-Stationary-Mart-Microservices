package com.onlinestationarymart.domain_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private Map<String,Object> userInfo;
    private Map<String,Integer> productsCodeList;
    private Double totalAmount;
    private String orderStatus;

    @Override
    public String toString() {
        return "OrderDTO{" +
                "orderId=" + orderId +
                ", userInfo=" + userInfo +
                ", productsCodeList=" + productsCodeList +
                ", totalAmount=" + totalAmount +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}
