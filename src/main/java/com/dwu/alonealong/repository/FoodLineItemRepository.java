package com.dwu.alonealong.repository;

import java.util.List;

import com.dwu.alonealong.domain.FoodLineItem;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface FoodLineItemRepository extends CrudRepository<FoodLineItem, String>{
	
	List<FoodLineItem> findByOrderId(String orderId) throws DataAccessException; 

}
