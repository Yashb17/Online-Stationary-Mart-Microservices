package com.onlinestationarymart.domain_service.utils;

import com.onlinestationarymart.domain_service.dto.ProductDTO;

import java.util.Comparator;

public class ProductNameComparator implements Comparator<ProductDTO> {

    @Override
    public int compare(ProductDTO p1, ProductDTO p2) {
        return p1.getProductName().compareTo(p2.getProductName());
    }
}
