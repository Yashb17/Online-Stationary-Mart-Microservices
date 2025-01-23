package com.onlinestationarymart.domain_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendEmailDTO {

    private String toEmail;
    private String fullName;
    private String eventCode;
    private Map<String, Integer> productsCodeMap;
    private List<ProductDTO> productDTOList;
    private Long orderId;
    private Double totalAmount;

    @Override
    public String toString() {
        return "SendEmailDTO{" +
                "toEmail='" + toEmail + '\'' +
                ", fullName='" + fullName + '\'' +
                ", eventCode='" + eventCode + '\'' +
                ", productsCodeMap=" + productsCodeMap +
                ", productDTOList=" + productDTOList +
                ", orderId=" + orderId +
                ", totalAmount=" + totalAmount +
                '}';
    }
}
