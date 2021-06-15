package com.dwu.alonealong.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.dwu.alonealong.domain.Together;

@Mapper
public interface TogetherMapper {
	Together getTogetherByTogId(String togId) throws DataAccessException;
	List<Together> getTogetherList() throws DataAccessException;
	List<Together> getTogetherListByCategory(String area, String date, String kind, int price, String sex, String age) throws DataAccessException;
	void insertTogether(Together together) throws DataAccessException;
	void updateTogether(Together together) throws DataAccessException;
	List<Together> recommandTogetherList(String sex, String address) throws DataAccessException;
	List<Together> getTogetherListByResId(String resId) throws DataAccessException;
}
