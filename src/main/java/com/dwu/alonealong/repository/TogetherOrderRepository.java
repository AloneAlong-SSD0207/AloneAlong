package com.dwu.alonealong.repository;

import com.dwu.alonealong.domain.TogetherOrder;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TogetherOrderRepository extends CrudRepository<TogetherOrder, String> {
    void deleteByTogetherId(String togId) throws DataAccessException;

    List<TogetherOrder> findByTogetherId(String togId) throws DataAccessException;

    @Query(value = "SELECT TOGORDER_ID_SEQ.NEXTVAL FROM DUAL", nativeQuery = true )
    long getTogOrderIdFromSeq() throws DataAccessException;

    //findByUserId도 추가해야 함

}
