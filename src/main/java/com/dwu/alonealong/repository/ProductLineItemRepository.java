package com.dwu.alonealong.repository;

import java.util.List;
import com.dwu.alonealong.domain.ProductLineItem;
import org.springframework.data.repository.CrudRepository;

public interface ProductLineItemRepository extends CrudRepository<ProductLineItem, Long> {
    List<ProductLineItem> findByOrderId(String orderId);
    boolean existsByProductOrder_Order_UserIdAndProductId(String userId, String productId);
}