package com.dwu.alonealong.repository;

import com.dwu.alonealong.domain.Together;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TogetherRepository extends CrudRepository<Together, String> {

    @Query(value = "SELECT TOG_ID_SEQ.NEXTVAL FROM DUAL", nativeQuery = true )
    long getTogIdFromSeq() throws DataAccessException;

    void deleteByTogetherId(String togId) throws DataAccessException;

    //searchTogetherList
    //getTogetherListByCategory
    //recommandTogetherList
    //getTogetherListByResId
}
