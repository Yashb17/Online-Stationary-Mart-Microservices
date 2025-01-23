package com.onlinestationarymart.inventory_service.service;

import com.onlinestationarymart.domain_service.dto.InventoryDTO;
import com.onlinestationarymart.domain_service.dto.ResponseDTO;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IInventoryService {
    InventoryDTO addProductInventoryDetails(InventoryDTO inventoryDTO);

    Optional<InventoryDTO> findProductInventoryDetailsByProductCode(String productCode);

    List<InventoryDTO> getAllProductInventoryDetails();

    InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO);

    Boolean deleteProduct(InventoryDTO inventoryDTO);

    Boolean checkInventoryStatusOk(Map<String, Integer> productsCodeList);

    ResponseDTO checkAndUpdateInventoryStatus(Map<String, Integer> productData);
}
