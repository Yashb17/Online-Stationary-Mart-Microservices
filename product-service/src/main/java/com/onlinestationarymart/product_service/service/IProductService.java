package com.onlinestationarymart.product_service.service;

import com.onlinestationarymart.domain_service.dto.FilterDTO;
import com.onlinestationarymart.domain_service.dto.ProductDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IProductService {
    ProductDTO updateProduct(Long id, ProductDTO productDTO);

    ProductDTO addProduct(ProductDTO productDTO);

    Optional<ProductDTO> findProductDTOById(Long id);

    List<ProductDTO> getAllProducts();

    Boolean deleteProduct(ProductDTO productDTO);

    List<ProductDTO> getAllProductsByFilterSort(FilterDTO filterDTO);

    double getTotalAmountForProducts(Map<String, Integer> productsOrdered);

    List<ProductDTO> getAllProductsByProductCodes(List<Long> productCodes);
}
