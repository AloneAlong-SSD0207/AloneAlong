package com.dwu.alonealong.repository;

import com.dwu.alonealong.domain.ProductOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductOrderRepository extends CrudRepository<ProductOrder, Long> {
    ProductOrder findByOrderId(String orderId);
    @Query(value = "SELECT PRODUCTORDERID_SEQ.NEXTVAL FROM DUAL", nativeQuery = true )
    long getOrderId();
}