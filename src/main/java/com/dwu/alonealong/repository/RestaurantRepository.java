package com.dwu.alonealong.repository;

import java.util.List;

import com.dwu.alonealong.domain.Restaurant;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;
public interface RestaurantRepository extends CrudRepository<Restaurant, Long>{//SQL Mapper와 유사. 엔티티와 꼭 같은 패키지.

	List<Restaurant> findByAreaContainingAndCategoryIdContainingAndOpenIsNotNullOrderByResDateDesc(String category1, String category2) throws DataAccessException;
	List<Restaurant> findByAreaContainingAndCategoryIdContainingAndOpenIsNotNullOrderByRevCountDesc(String category1, String category2) throws DataAccessException;
	List<Restaurant> findByAreaContainingAndCategoryIdContainingAndOpenIsNotNullOrderByAvgRatingDesc(String category1, String category2) throws DataAccessException;

	List<Restaurant> findByResNameContainingIgnoreCaseOrResDescriptionContainingIgnoreCase(String keywords, String keywords2) throws DataAccessException;
	Restaurant findByOwnerId(String userId) throws DataAccessException;
	Restaurant findByResId(long resId) throws DataAccessException;

}
