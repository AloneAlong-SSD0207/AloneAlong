package com.dwu.alonealong.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface FoodOrderInfoRepository extends CrudRepository<Order, String> {
	List<Order> findByUserIdOrderByOrderDateDesc(String userId);
}
