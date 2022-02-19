package com.dwu.alonealong.repository;

import java.util.List;

import com.dwu.alonealong.domain.ProductReview;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductReviewRepository extends CrudRepository<ProductReview, Long> {
    //get ProductReview
    ProductReview findByReviewId(long reviewId) throws DataAccessException;

    //get ProductReview List
    List<ProductReview> findByProductId(String productId, Sort sort) throws DataAccessException; //new

    int deleteByReviewId(long reviewId) throws DataAccessException;

    //ReviewList Info
    int countByProductIdAndRating(String productId, int rating) throws DataAccessException;
    @Query(value = "SELECT REVIEW_RATING " +
            "FROM Product_REVIEW " +
            "WHERE PRODUCT_ID=:productId " +
            "GROUP BY REVIEW_RATING ORDER BY COUNT(*) DESC", nativeQuery = true )
    List<Integer> mostRatingOfReviews(@Param("productId") String productId) throws DataAccessException;
}
