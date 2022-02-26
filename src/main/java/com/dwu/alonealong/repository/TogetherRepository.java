package com.dwu.alonealong.repository;

import com.dwu.alonealong.domain.Together;
import com.dwu.alonealong.domain.TogetherOrder;
import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TogetherRepository extends JpaRepository<Together, String> {

    Together getByTogetherId(String togId) throws DataAccessException;

    @Query(value = "SELECT TOG_ID_SEQ.NEXTVAL FROM DUAL", nativeQuery = true )
    long getTogIdFromSeq() throws DataAccessException;

    void deleteByTogetherId(String togId) throws DataAccessException;

    @Query("SELECT t, r FROM Together t, Restaurant r WHERE t.resId = r.resId AND t.resId = ?1 AND t.status = 0")
    List<Together> findGatheringsByResId(String resId) throws DataAccessException;

    @Query("SELECT t, r FROM Together t, Restaurant r WHERE t.resId = r.resId AND t.status = 0 AND (t.sex = ?1 OR t.sex = '상관없음') AND r.resAddress LIKE %?2%")
    List<Together> findGatheringsBySexAndAddress(String sex, String address) throws DataAccessException;

    @Query("SELECT t, r FROM Together t, Restaurant r WHERE t.resId = r.resId AND t.status = 0 AND (t.togetherName LIKE %?1%)")
    List<Together> findGatheringsByKeyword(String keyword) throws DataAccessException;

    @Query("SELECT t, r FROM Together t, Restaurant r WHERE t.resId = r.resId AND t.status = 0 AND r.area LIKE %?1% AND t.togetherDate LIKE %?2% AND r.categoryId LIKE %?3% AND t.price < ?4 AND t.sex LIKE %?5% AND t.age LIKE %?6% ORDER BY t.togetherId DESC")
    List<Together> findGatheringsByCategory(String area, String date, String kind, int price, String sex, String age) throws DataAccessException;
}
