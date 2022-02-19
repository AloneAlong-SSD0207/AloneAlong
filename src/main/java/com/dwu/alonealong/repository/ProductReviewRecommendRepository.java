package com.dwu.alonealong.repository;

import com.dwu.alonealong.domain.ProductReviewRecommend;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface ProductReviewRecommendRepository extends CrudRepository<ProductReviewRecommend, Long> {
    boolean existsByReviewIdAndUserId(long reviewId, String userId) throws DataAccessException;
    int deleteByReviewId(long reviewId) throws DataAccessException;
    int deleteByReviewIdAndUserId(long reviewId, String userId) throws DataAccessException;
}