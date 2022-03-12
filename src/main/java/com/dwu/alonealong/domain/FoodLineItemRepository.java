package com.dwu.alonealong.domain;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface FoodLineItemRepository extends CrudRepository<FoodLineItem, String>{
	
	List<FoodLineItem> findByOrderId(String orderId) throws DataAccessException; 

}
