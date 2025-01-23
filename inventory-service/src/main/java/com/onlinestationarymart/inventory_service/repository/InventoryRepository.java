package com.onlinestationarymart.inventory_service.repository;

import com.onlinestationarymart.inventory_service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    @Query(value = "SELECT inv FROM Inventory inv WHERE productCode =:code")
    public Inventory findByProductCode(@Param("code") String productCode);

    @Modifying
    @Query(value = "UPDATE Inventory inv SET stockQuantity =:dbQuantity - :quantity " +
            "WHERE productCode =:code")
    Integer updateByProductCode(@Param("code") String code, @Param("quantity") Integer quantity,
                             @Param("dbQuantity") Integer dbInventoryCount);
}
