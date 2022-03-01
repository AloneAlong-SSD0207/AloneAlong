package com.dwu.alonealong.repository;

import java.util.List;

import com.dwu.alonealong.domain.FoodReview;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface FoodReviewRepository extends CrudRepository<FoodReview, String> {
	List<FoodReview> findByResIdOrderByReviewDateDesc(long resId) throws DataAccessException;
	List<FoodReview> findByResIdOrderByRatingDesc(long resId) throws DataAccessException;
	List<FoodReview> findByResIdOrderByRatingAsc(long resId) throws DataAccessException;
}
