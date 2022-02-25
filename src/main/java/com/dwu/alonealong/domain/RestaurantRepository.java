package com.dwu.alonealong.domain;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
public interface RestaurantRepository extends CrudRepository<Restaurant, Long>{//SQL Mapper와 유사. 엔티티와 꼭 같은 패키지.

	List<Restaurant> findByAreaContainingAndCategoryIdContainingOrderByResDateDesc(String category1, String category2) throws DataAccessException;
	List<Restaurant> findByAreaContainingAndCategoryIdContainingOrderByRevCountDesc(String category1, String category2) throws DataAccessException;
	List<Restaurant> findByAreaContainingAndCategoryIdContainingOrderByAvgRatingDesc(String category1, String category2) throws DataAccessException;

	List<Restaurant> findByResNameContainingIgnoreCaseOrResDescriptionContainingIgnoreCase(String keywords, String keywords2) throws DataAccessException;
	Restaurant findByOwnerId(String userId) throws DataAccessException;
	Restaurant findByResId(long resId) throws DataAccessException;

}
