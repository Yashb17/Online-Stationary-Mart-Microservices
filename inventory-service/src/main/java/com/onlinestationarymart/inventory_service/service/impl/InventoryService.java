package com.onlinestationarymart.inventory_service.service.impl;

import com.onlinestationarymart.domain_service.Exception.ResourceNotFoundException;
import com.onlinestationarymart.domain_service.dto.InventoryDTO;
import com.onlinestationarymart.domain_service.dto.ResponseDTO;
import com.onlinestationarymart.inventory_service.entity.Inventory;
import com.onlinestationarymart.inventory_service.repository.InventoryRepository;
import com.onlinestationarymart.inventory_service.service.IInventoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class InventoryService implements IInventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryService.class);

    @Override
    public InventoryDTO addProductInventoryDetails(InventoryDTO inventoryDTO) {
        LOGGER.info("@@@@ InventoryService: Inside addProductInventoryDetails method data-> {}", inventoryDTO);
        Inventory inventory = modelMapper.map(inventoryDTO, Inventory.class);
        Inventory createdProductInventory = inventoryRepository.save(inventory);
        InventoryDTO createdProductInventoryDTO = modelMapper.map(createdProductInventory, InventoryDTO.class);
        LOGGER.info("@@@@ InventoryService: Created Product -> {}", createdProductInventoryDTO.toString());
        return createdProductInventoryDTO;
    }

    @Override
    public Optional<InventoryDTO> findProductInventoryDetailsByProductCode(String productCode) {
        LOGGER.info("@@@@ ProductService: Inside findProductDTOById method trying to fetch existing Product by Id: {}", productCode);
        Inventory inventory = inventoryRepository.findByProductCode(productCode);
        if(Objects.isNull(inventory)){
            throw new NullPointerException();
        }
        return Optional.ofNullable(modelMapper.map(inventory, InventoryDTO.class));
    }

    @Override
    public List<InventoryDTO> getAllProductInventoryDetails() {
        LOGGER.info("@@@@ InventoryService: Inside getAllProductInventoryDetails method to fetch all Inventory");
        List<Inventory> inventoryList = inventoryRepository.findAll();
        return inventoryList.stream().map(e -> modelMapper.map(e, InventoryDTO.class)).toList();
    }

    @Override
    @Transactional
    public InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO) {
        LOGGER.info("@@@@ InventoryService: Inside updateProduct method trying to find existing Product by Id: {}", id);
        Inventory existingProductInventory = inventoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Inventory", "id", inventoryDTO.getId())
        );
        if(Objects.nonNull(existingProductInventory)) {
            existingProductInventory.setProductCode(inventoryDTO.getProductCode());
            existingProductInventory.setStockQuantity(inventoryDTO.getStockQuantity());
            Inventory updatedProductInventory  = inventoryRepository.save(existingProductInventory);
            InventoryDTO updatedProductInventoryDTO = modelMapper.map(updatedProductInventory, InventoryDTO.class);
            LOGGER.info("@@@@ InventoryService: Updated Inventory -> {}", updatedProductInventoryDTO.toString());
            return updatedProductInventoryDTO;
        }
        else{
            return null;
        }
    }

    @Override
    @Transactional
    public Boolean deleteProduct(InventoryDTO inventoryDTO) {
        LOGGER.info("@@@@ InventoryService: Inside deleteProductInventory method, delete existing InventoryId: {}", inventoryDTO.getId());
        Inventory existingInventory = findInventoryById(inventoryDTO.getId());
        LOGGER.info("@@@@ InventoryService: Found Inventory!. Going to delete it");
        inventoryRepository.deleteById(existingInventory.getId());
        return Boolean.TRUE;
    }

    @Override
    public Boolean checkInventoryStatusOk(Map<String, Integer> productsCodeList) {
        for (Map.Entry<String,Integer> entry : productsCodeList.entrySet()) {
            LOGGER.info("@@@ InventoryService: checkInventoryStatus for key:{} & value:{}", entry.getKey(), entry.getValue());
            Integer dbInventoryCount = inventoryRepository.findByProductCode(entry.getKey()).getStockQuantity();
            LOGGER.info("@@@ InventoryService: Stock Quantity for Product Code: {} is: {}", entry.getKey(), dbInventoryCount);
            if(dbInventoryCount < entry.getValue()) {
                return Boolean.FALSE;
            }
    }
        return Boolean.TRUE;
}

    @Override
    @Transactional
    public ResponseDTO checkAndUpdateInventoryStatus(Map<String, Integer> productsCodeList) {
        ResponseDTO responseDTO = new ResponseDTO();
        for (Map.Entry<String,Integer> entry : productsCodeList.entrySet()) {
            LOGGER.info("@@@ InventoryService: checkInventoryStatus for key:{} & value:{}", entry.getKey(), entry.getValue());
            Integer dbInventoryCount = inventoryRepository.findByProductCode(entry.getKey()).getStockQuantity();
            LOGGER.info("@@@ InventoryService: Stock Quantity for Product Code: {} is: {}", entry.getKey(), dbInventoryCount);
            if(dbInventoryCount < entry.getValue()) {
                responseDTO.setSuccess(Boolean.FALSE);
                responseDTO.setMessage(String.format("Product Quantity Requested is not available for %s -> %s",entry.getKey(), entry.getValue()));
                return responseDTO;
            }
            else{
                Integer success = inventoryRepository.updateByProductCode(entry.getKey(), entry.getValue(), dbInventoryCount);
                LOGGER.info("@@@@ InventoryService: Updated DB: {}", success.equals(Integer.parseInt("1")));
            }
        }
        responseDTO.setMessage("Yeah Yeah! Inventory Updated Successfully!");
        responseDTO.setSuccess(Boolean.TRUE);
        return responseDTO;
    }

    public Inventory findInventoryById(Long id) {
        return inventoryRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Inventory", "id", id)
        );
    }
}
