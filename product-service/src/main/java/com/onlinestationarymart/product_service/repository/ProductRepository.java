package com.onlinestationarymart.product_service.repository;

import com.onlinestationarymart.product_service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query(value = "SELECT pro FROM Product pro WHERE productCode =:code")
    Product findbyProductCode(@Param("code") String productCode);

}
