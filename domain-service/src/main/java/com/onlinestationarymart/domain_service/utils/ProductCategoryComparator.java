package com.onlinestationarymart.domain_service.utils;

import com.onlinestationarymart.domain_service.dto.ProductDTO;

import java.util.Comparator;

public class ProductCategoryComparator implements Comparator<ProductDTO> {

    @Override
    public int compare(ProductDTO p1, ProductDTO p2) {
        return p1.getCategory().compareTo(p2.getCategory());
    }
}
