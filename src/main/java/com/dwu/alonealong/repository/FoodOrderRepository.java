package com.dwu.alonealong.repository;

import java.util.List;

import com.dwu.alonealong.domain.FoodOrder;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
public interface FoodOrderRepository extends CrudRepository<FoodOrder, String>{
	FoodOrder findByOrderId(String orderId) throws DataAccessException;
	@Query(value = "SELECT FOODORDERID_SEQ.NEXTVAL FROM DUAL", nativeQuery = true)
	long getNewOrderId();
}
