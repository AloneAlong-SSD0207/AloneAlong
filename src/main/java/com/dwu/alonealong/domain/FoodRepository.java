package com.dwu.alonealong.domain;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<Food, Long>{
	List<Food> findByResId(long resId) throws DataAccessException;
	Food findByFoodId(Long foodId) throws DataAccessException;
}
