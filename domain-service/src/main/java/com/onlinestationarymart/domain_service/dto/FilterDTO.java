package com.onlinestationarymart.domain_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FilterDTO {

    private Boolean isFilterSortRequired;
    private String sortBy;

}
