package com.dwu.alonealong.repository;

import com.dwu.alonealong.domain.TogetherFood;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TogetherFoodRepository extends CrudRepository<TogetherFood, String> {
    List<TogetherFood> findByTogetherId(String togId) throws DataAccessException;

    @Query(value = "SELECT TOGFOOD_ID_SEQ.NEXTVAL FROM DUAL", nativeQuery = true )
    long getTogFoodIdFromSeq() throws DataAccessException;

    void deleteByTogetherId(String togId) throws DataAccessException;
}
