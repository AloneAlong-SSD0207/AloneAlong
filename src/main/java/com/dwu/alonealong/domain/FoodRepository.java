package com.dwu.alonealong.domain;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<Food, String>{
	List<Food> findByResId(String resId) throws DataAccessException;
	Food findByFoodId(String foodId) throws DataAccessException;
}
