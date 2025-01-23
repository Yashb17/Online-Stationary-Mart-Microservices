package com.onlinestationarymart.batch_service.prcoessor;

import com.onlinestationarymart.batch_service.dto.CSVData;
import com.onlinestationarymart.domain_service.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Component
public class CustomProcessor implements ItemProcessor<CSVData, OrderDTO>{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomProcessor.class);
	private static final String UNDERSCORE = "_";

    @Override
    public OrderDTO process(CSVData csvData) throws Exception, NullPointerException {
    	LOGGER.info("Inside CustomProcessor");
        Map<String, Object> userInfo = new HashMap<>();
        OrderDTO orderDTO = new OrderDTO();
        Map<String,Integer> productsCodeMap = new HashMap<>();
        
        try {
			validateValues(csvData);
			setUserInfoFields(csvData, userInfo, orderDTO);
			
			String[] productCodes = csvData.getProductCodeString().split(UNDERSCORE);
			String[] productQuantities = csvData.getProductQuantityString().split(UNDERSCORE);
			if(productCodes.length != productQuantities.length) {
				 LOGGER.error("ProductCodeString and ProductQuantityString doesnt have equal size");
				 throw new Exception();
			}
			for(int i = 0; i < productCodes.length; i++) {
				productsCodeMap.put(productCodes[i], Integer.valueOf(productQuantities[i]));
			}
			orderDTO.setProductsCodeList(productsCodeMap);
			LOGGER.info("CustomProcessor: OrderDTO processed -> {}", orderDTO.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
        return orderDTO;
    }

	private void setUserInfoFields(CSVData csvData, Map<String, Object> userInfo, OrderDTO orderDTO) {
		userInfo.put("userid", String.valueOf(csvData.getUserid()));
        userInfo.put("fullName", csvData.getFullName());
        userInfo.put("email", csvData.getEmail());
        userInfo.put("address", csvData.getAddress());
        orderDTO.setUserInfo(userInfo);
	}

	private void validateValues(CSVData csvData) {
		try {
			if(Objects.isNull(csvData.getUserid())){
				LOGGER.error("UserId is null in CSV data");
				 throw new NullPointerException();
			}
			if(Objects.isNull(csvData.getFullName())){
				LOGGER.error("UserId is null in CSV data");
				 throw new NullPointerException();
			}
			if(Objects.isNull(csvData.getEmail())){
				LOGGER.error("UserId is null in CSV data");
				 throw new NullPointerException();
			}
			if(Objects.isNull(csvData.getAddress())){
				LOGGER.error("UserId is null in CSV data");
				 throw new NullPointerException();
			}
			if(Objects.isNull(csvData.getProductCodeString())){
				LOGGER.error("UserId is null in CSV data");
				 throw new NullPointerException();
			}
			if(Objects.isNull(csvData.getProductQuantityString())){
				LOGGER.error("UserId is null in CSV data");
				 throw new NullPointerException();
			}
		} catch (NullPointerException e) {
			LOGGER.error("UserId is null in CSV data: ", e);
			e.printStackTrace();
		}
	}

}
