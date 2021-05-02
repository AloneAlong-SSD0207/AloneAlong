package com.dwu.alonealong.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.dwu.alonealong.domain.FoodReview;
import com.dwu.alonealong.domain.ProductReview;

public interface ProductReviewDAO {
	//get ProductReview List Method
	List<ProductReview> getReviewsByProductId(String productId) throws DataAccessException;
	List<ProductReview> getReviewsByUserId(String userId) throws DataAccessException;
//	//����
//	// List<ProductReview> SortByNew(String productId) throws DataAccessException; > �⺻
//	List<ProductReview> SortByRecommend(String productId) throws DataAccessException;
//	List<ProductReview> SortByHighestRating(String productId) throws DataAccessException;
//	List<ProductReview> SortByLowestRating(String productId) throws DataAccessException;
//
//	//�ʹ� ���������ϸ� ��...(���)
//	int NumOfReviews(String productId) throws DataAccessException;
//	double AverageOfReviews(String productId) throws DataAccessException;
//	int MostRatingOfReviews(String productId) throws DataAccessException;
	
	void insertProductReview(ProductReview productReview);
	void deleteProductReview(ProductReview productReview);

}
