package com.onlinestationarymart.product_service.service.impl;

import com.onlinestationarymart.domain_service.Exception.ResourceNotFoundException;
import com.onlinestationarymart.domain_service.dto.FilterDTO;
import com.onlinestationarymart.domain_service.dto.ProductDTO;
import com.onlinestationarymart.domain_service.utils.ProductCategoryComparator;
import com.onlinestationarymart.domain_service.utils.ProductNameComparator;
import com.onlinestationarymart.product_service.entity.Product;
import com.onlinestationarymart.product_service.repository.ProductRepository;
import com.onlinestationarymart.product_service.service.IProductService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ProductService implements IProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductService.class);

    private static final String PRODUCT_NAME = "productName";
    private static final String PRODUCT_CATEGORY = "category";

    @Override
    @Transactional
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        LOGGER.info("@@@@ ProductService: Inside updateProduct method trying to find existing Product by Id: {}", id);
        Product existingProduct = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", productDTO.getId())
        );
        if(Objects.nonNull(existingProduct)) {
            existingProduct.setProductName(productDTO.getProductName());
            existingProduct.setProductCode(productDTO.getProductCode());
            existingProduct.setAmount(productDTO.getAmount());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setCategory(productDTO.getCategory());
            Product updatedProduct  = productRepository.save(existingProduct);
            ProductDTO updatedProductDTO = modelMapper.map(updatedProduct, ProductDTO.class);
            LOGGER.info("@@@@ ProductService: Updated Product -> {}", updatedProductDTO.toString());
            return updatedProductDTO;
        }
        else{
            return null;
        }
    }

    @Override
    public ProductDTO addProduct(ProductDTO productDTO) {
        LOGGER.info("@@@@ ProductService: Inside addProduct method for creating a new Product with data-> {}", productDTO);
        Product product = modelMapper.map(productDTO, Product.class);
        Product createdProduct = productRepository.save(product);
        ProductDTO createdProductDTO = modelMapper.map(createdProduct, ProductDTO.class);
        LOGGER.info("@@@@ ProductService: Created Product -> {}", createdProductDTO.toString());
        return createdProductDTO;
    }

    @Override
    public Optional<ProductDTO> findProductDTOById(Long id) {
        LOGGER.info("@@@@ ProductService: Inside findProductDTOById method trying to fetch existing Product by Id: {}", id);
        Product product = productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", id)
        );
        return Optional.ofNullable(modelMapper.map(product, ProductDTO.class));
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        LOGGER.info("@@@@ ProductService: Inside getAllProducts method to fetch all Products");
        List<Product> product = productRepository.findAll();
        return product.stream().map(e -> modelMapper.map(e, ProductDTO.class)).toList();
    }

    @Override
    @Transactional
    public Boolean deleteProduct(ProductDTO productDTO) {
        LOGGER.info("@@@@ ProductService: Inside deleteProduct method trying to delete existing Product by Id: {}", productDTO.getId());
        Product existingUser = findProductById(productDTO.getId());
        LOGGER.info("@@@@ ProductService: Found Product!. Going to delete it");
        productRepository.deleteById(existingUser.getId());
        return Boolean.TRUE;
    }

    @Override
    public List<ProductDTO> getAllProductsByFilterSort(FilterDTO filterDTO) {
        LOGGER.info("@@@@ ProductService: Inside filterSortMethod to filter by: {}", filterDTO.toString());
        List<Product> product = productRepository.findAll();
        List<ProductDTO> productDTOList = new java.util.ArrayList<>(product.stream().map(e -> modelMapper.map(e, ProductDTO.class)).toList());
        if(filterDTO.getSortBy().equalsIgnoreCase(PRODUCT_NAME)){
            productDTOList.sort(new ProductNameComparator());
            LOGGER.info("@@@@ ProductService: After sort: {}", productDTOList.toString());
            return productDTOList;
        } else if (filterDTO.getSortBy().equalsIgnoreCase(PRODUCT_CATEGORY)) {
            productDTOList.sort(new ProductCategoryComparator());
            LOGGER.info("@@@@ ProductService: After sort: {}", productDTOList.toString());
            return productDTOList;
        }
        else {
            Collections.sort(productDTOList);
            LOGGER.info("@@@@ ProductService: After sort: {}", productDTOList.toString());
            return productDTOList;
        }
    }

    @Override
    public double getTotalAmountForProducts(Map<String, Integer> productsOrdered) {
        LOGGER.info("@@@@ ProductService: Inside getTotalAmountForProducts for products: {}", productsOrdered);
        double totalAmount = 0D;
        for (Map.Entry<String,Integer> entry : productsOrdered.entrySet()) {
            totalAmount = totalAmount + (productRepository.findbyProductCode(entry.getKey()).getAmount() * entry.getValue());
        }
        return totalAmount;
    }

    @Override
    public List<ProductDTO> getAllProductsByProductCodes(List<Long> productCodes) {
        LOGGER.info("@@@@ ProductService: Inside getAllProducts method to fetch all Products");
        List<Product> product = productRepository.findAll();
        List<ProductDTO> productDTOList =  product.stream().map(e -> modelMapper.map(e, ProductDTO.class)).toList();
        LOGGER.info("@@@@ ProductService: got productDTOList: {}", productDTOList.toString());
        return productDTOList.stream()
                .filter(e -> productCodes.contains(e.getProductCode()))
                .toList();
    }

    public Product findProductById(Long id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Product", "id", id)
        );
    }
}
