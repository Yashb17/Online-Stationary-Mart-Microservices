package com.onlinestationarymart.inventory_service.controller;

import com.onlinestationarymart.domain_service.dto.InventoryDTO;
import com.onlinestationarymart.domain_service.dto.ResponseDTO;
import com.onlinestationarymart.inventory_service.service.IInventoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/inventory")
public class InventoryServiceController {

    @Autowired
    private IInventoryService inventoryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryServiceController.class);


    //Post Request - addProductInventoryDetails http://localhost:8080/api/inventory/addProductInventoryDetails
    @PostMapping("addProductInventoryDetails")
    public ResponseEntity<InventoryDTO> addProduct(@RequestBody InventoryDTO inventoryDTO){
        LOGGER.info("@@@@ InventoryController: Received add Inventory Request with Inventory: {}", inventoryDTO);
        InventoryDTO inventorySavedDTO = inventoryService.addProductInventoryDetails(inventoryDTO);
        return new ResponseEntity<>(inventorySavedDTO, HttpStatus.CREATED);
    }

    //Get Request - findByProductCode  http://localhost:8080/api/inventory/findProductInventoryDetails?productCode=
    @GetMapping("/findProductInventoryDetails")
    public InventoryDTO findProductInventoryDetailsByProductCode(@RequestParam String productCode){
        LOGGER.info("@@@@ InventoryController: Received find Product Inventory Request with ProductCode: {}", productCode);
        return inventoryService.findProductInventoryDetailsByProductCode(productCode).orElse(null);
    }

    //Get Request - getAllProductInventoryDetails  http://localhost:8080/api/product/getAllProductInventoryDetails
    @GetMapping("/getAllProductInventoryDetails")
    public List<InventoryDTO> getAllProductInventoryDetails(){
        LOGGER.info("@@@@ InventoryController: Received get All Products Inventory Details");
        return inventoryService.getAllProductInventoryDetails();
    }

    //Put Request - updateInventory http://localhost:8080/api/inventory/updateInventory/2
    @PutMapping("updateInventory/{id}")
    public ResponseEntity<InventoryDTO> updateInventory(@PathVariable Long id, @RequestBody InventoryDTO inventoryDTO) {
        LOGGER.info("@@@@ InventoryController: Received update Inventory Request with InventoryDTO: {}", inventoryDTO);
        InventoryDTO savedInventoryDTO = inventoryService.updateInventory(id, inventoryDTO);
        return savedInventoryDTO != null ? new ResponseEntity<>(savedInventoryDTO, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //Delete Request - deleteInventory  http://localhost:8080/api/inventory/deleteInventory
    @DeleteMapping("deleteInventory")
    public ResponseEntity<String> deleteProduct(@RequestBody InventoryDTO inventoryDTO) {
        LOGGER.info("@@@@ InventoryController: Received delete Inventory Request for Inventory: {}", inventoryDTO);
        Boolean inventoryDeleted = inventoryService.deleteProduct(inventoryDTO);
        return inventoryDeleted != Boolean.FALSE ? new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK) :
                new ResponseEntity<>("Ohh No,Product doesn't exist in database!", HttpStatus.OK);
    }

    //Get Request - checkInventoryStatusOk  http://localhost:8080/api/inventory/checkInventoryStatusOk
    @GetMapping("/checkInventoryStatusOk")
    public Boolean checkInventoryStatusOk(@RequestBody Map<String,Integer> productsCodeList){
        LOGGER.info("@@@@ InventoryController: Received checkInventoryStatusOk Request for Products: {}", productsCodeList);
        return inventoryService.checkInventoryStatusOk(productsCodeList);
    }

    //PostRequest - updateInventoryForOrder http://localhost:8080/api/inventory/checkAndUpdateInventoryStatus
    @PostMapping("checkAndUpdateInventoryStatus")
    public ResponseEntity<ResponseDTO> checkAndUpdateInventoryStatus(@RequestBody Map<String, Integer> productData) {
        LOGGER.info("@@@@ InventoryController: Received update Inventory Request with Map: {}", productData);
        ResponseDTO responseDTO = inventoryService.checkAndUpdateInventoryStatus(productData);
        return new ResponseEntity<>(responseDTO, HttpStatus.OK);
    }
}
