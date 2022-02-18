package com.dwu.alonealong.repository;

import java.util.List;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import com.dwu.alonealong.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Long>{
    //get Product Method
    Product findByProductId(String productId) throws DataAccessException;

    //검색
    List<Product> findByProductNameContaining(String keyword) throws DataAccessException;

    //카테고리별 반환
    List<Product> findByPcIdOrderByProductDateDesc(String pcId) throws DataAccessException; //new
    List<Product> findByPcIdOrderByProductDateAsc(String pcId) throws DataAccessException; //past
    List<Product> findByPcIdOrderByProductSalesDesc(String pcId) throws DataAccessException; //sales
    List<Product> findByPcIdOrderByProductPriceAsc(String pcId) throws DataAccessException; //price
//    List<Product> getProductList(int pcId, String sortType) throws DataAccessException;
}
