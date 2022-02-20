package com.dwu.alonealong.repository;

import com.dwu.alonealong.domain.TogetherMember;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TogetherMemberRepository extends CrudRepository<TogetherMember, String>{
    List<TogetherMember> findByTogetherId(String togId) throws DataAccessException;

    @Query(value = "SELECT TOGMEM_ID_SEQ.NEXTVAL FROM DUAL", nativeQuery = true )
    long getTogMemIdFromSeq();

    void deleteByTogetherId(String togId) throws DataAccessException;

}
