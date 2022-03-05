package com.dwu.alonealong.repository;

import java.util.List;

import com.dwu.alonealong.domain.Food;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface FoodRepository extends CrudRepository<Food, Long>{
	List<Food> findByResId(long resId) throws DataAccessException;
	Food findByFoodId(Long foodId) throws DataAccessException;
}
