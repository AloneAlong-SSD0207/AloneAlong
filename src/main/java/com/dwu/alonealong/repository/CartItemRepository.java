package com.dwu.alonealong.repository;

import java.util.List;

import com.dwu.alonealong.domain.CartItem;
import org.springframework.dao.DataAccessException;
import org.springframework.data.repository.CrudRepository;

public interface CartItemRepository extends CrudRepository<CartItem, Long>{
    //get CartItem
    CartItem findByCartItemId(long cartItemId) throws DataAccessException;

    //get CartItem List
    List<CartItem> findByUserId(String userId) throws DataAccessException;

    int deleteByCartItemId(long cartItemId) throws DataAccessException;
    int deleteByUserId(String userId) throws DataAccessException;
    CartItem findByUserIdAndProductId(String userId, String productId);
}
