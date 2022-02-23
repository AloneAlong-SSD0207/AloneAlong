package com.dwu.alonealong.repository;

import com.dwu.alonealong.domain.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository  extends CrudRepository<Order, Long> {
    Order findByOrderId(String orderId);
    List<Order> findByUserId(String userId);
    boolean existsByOrderIdAndUserId(String orderId, String userId);

    void deleteByOrOrderId(String orderId) throws DataAccessException;
}