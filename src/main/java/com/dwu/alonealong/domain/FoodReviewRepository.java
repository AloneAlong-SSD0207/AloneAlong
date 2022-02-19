package com.dwu.alonealong.domain;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface FoodReviewRepository extends CrudRepository<FoodReview, String> {
	List<FoodReview> findByResIdOrderByReviewDateDesc(String resId) throws DataAccessException; 
	List<FoodReview> findByResIdOrderByRatingDesc(String resId) throws DataAccessException; 
	List<FoodReview> findByResIdOrderByRatingAsc(String resId) throws DataAccessException; 
}
