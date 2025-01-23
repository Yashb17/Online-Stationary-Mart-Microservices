package com.onlinestationarymart.product_service.controller;

import com.onlinestationarymart.domain_service.dto.FilterDTO;
import com.onlinestationarymart.domain_service.dto.ProductDTO;
import com.onlinestationarymart.product_service.service.IProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/product")
public class ProductServiceController {

    @Autowired
    private IProductService productService;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceController.class);

    //Post Request - addProduct  http://localhost:8084/api/product/addProduct
    @PostMapping("addProduct")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO productDTO){
        LOGGER.info("@@@@ ProductController: Received add product Request with Product: {}", productDTO);
        ProductDTO productSavedDTO = productService.addProduct(productDTO);
        return new ResponseEntity<>(productSavedDTO, HttpStatus.CREATED);
    }

    //Get Request - findById  http://localhost:8084/api/product/findProduct?id=1
    @GetMapping("/findProduct")
    public ProductDTO findProductsById(@RequestParam Long id){
        LOGGER.info("@@@@ ProductController: Received find Product Request with id: {}", id);
        return productService.findProductDTOById(id).orElse(null);
    }

    //Get Request - getAllProducts  http://localhost:8084/api/product/getAllProducts
    @GetMapping("/getAllProducts")
    public List<ProductDTO> findAllProducts(){
        LOGGER.info("@@@@ ProductController: Received get All Products");
        return productService.getAllProducts();
    }

    //Get Request - getAllProducts  http://localhost:8084/api/product/getAllProductsByProductCodes
    @PostMapping("/getAllProductsByProductCodes")
    public List<ProductDTO> getAllProductsByProductCodes(@RequestBody List<Long> productCodes){
        LOGGER.info("@@@@ ProductController: Received get All Products by codes");
        return productService.getAllProductsByProductCodes(productCodes);
    }

    //Put Request - updateProduct http://localhost:8084/api/product/updateProduct/2
    @PutMapping("updateProduct/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        LOGGER.info("@@@@ ProductController: Received update Product Request with Product: {}", productDTO);
        ProductDTO userSavedDTO = productService.updateProduct(id, productDTO);
        return userSavedDTO != null ? new ResponseEntity<>(userSavedDTO, HttpStatus.OK) : new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
    }

    //Delete Request - deleteProduct  http://localhost:8084/api/product/deleteProduct
    @DeleteMapping("deleteProduct")
    public ResponseEntity<String> deleteProduct(@RequestBody ProductDTO productDTO) {
        LOGGER.info("@@@@ ProductController: Received delete Product Request for Product: {}", productDTO);
        Boolean productDeleted = productService.deleteProduct(productDTO);
        return productDeleted != Boolean.FALSE ? new ResponseEntity<>("Product deleted successfully!", HttpStatus.OK) :
                new ResponseEntity<>("Ohh No,Product doesn't exist in database!", HttpStatus.OK);
    }

    //Get Request - getAllProducts  http://localhost:8084/api/product/getAllProductsByFilterSort
    @GetMapping("/getAllProductsByFilterSort")
    public List<ProductDTO> getAllProductsByFilterSort(@RequestBody FilterDTO filterDTO){
        LOGGER.info("@@@@ ProductController: Received get All Product By filter SOrt request");
        return productService.getAllProductsByFilterSort(filterDTO);
    }

    //Get Request - getTotalAmountForProducts  http://localhost:8084/api/product/getTotalAmountForProducts
    @PostMapping("/getTotalAmountForProducts")
    public double getTotalAmountForProducts(@RequestBody Map<String,Integer> productsOrdered){
        LOGGER.info("@@@@ ProductController: Received getTotalAmountForProducts request");
        return productService.getTotalAmountForProducts(productsOrdered);
    }



}
