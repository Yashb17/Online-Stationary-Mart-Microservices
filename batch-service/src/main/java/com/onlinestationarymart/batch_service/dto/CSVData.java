package com.onlinestationarymart.batch_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CSVData {
    private Long userid;
    private String fullName;
    private String email;
    private String address;
    private String productCodeString;
    private String productQuantityString;
}
