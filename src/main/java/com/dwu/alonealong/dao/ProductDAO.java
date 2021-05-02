package com.dwu.alonealong.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.dwu.alonealong.domain.Product;

public interface ProductDAO {

	//get Product List Method
	//ī�װ��� ��ȯ
	List<Product> getProductListByCategory(String pcId) throws DataAccessException;
	//����
	List<Product> SortBySales(String pcId) throws DataAccessException;
	List<Product> SortByPast(String pcId) throws DataAccessException;
	//List<Product> SortByNew(String pcId) throws DataAccessException; > �⺻
	List<Product> SortByLowPrice(String pcId) throws DataAccessException;
	//�˻�
	List<Product> searchProductList(String keywords) throws DataAccessException;

	//get Product Method
	Product getProduct(String productId) throws DataAccessException;
}

