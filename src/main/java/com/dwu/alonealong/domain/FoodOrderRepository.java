package com.dwu.alonealong.domain;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
public interface FoodOrderRepository extends CrudRepository<FoodOrder, String>{
	FoodOrder findByOrderId(String orderId) throws DataAccessException;
	//List<FoodOrder> findByUserId(String userId) throws DataAccessException;
}
