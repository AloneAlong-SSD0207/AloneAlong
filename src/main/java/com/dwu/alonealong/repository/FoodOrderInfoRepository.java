package com.dwu.alonealong.repository;

import java.util.List;

import com.dwu.alonealong.domain.Order;
import org.springframework.data.repository.CrudRepository;

public interface FoodOrderInfoRepository extends CrudRepository<Order, String> {
	List<Order> findByUserIdAndOrderIdStartingWithOrderByOrderDateDesc(String userId, String orderCategory);
}
