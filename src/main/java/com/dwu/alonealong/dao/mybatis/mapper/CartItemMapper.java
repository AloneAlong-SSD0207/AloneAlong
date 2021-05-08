/*
 *    Copyright 2010-2013 the original author or authors.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.dwu.alonealong.dao.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.dao.DataAccessException;

import com.dwu.alonealong.domain.CartItem;


@Mapper
public interface CartItemMapper {

	List<CartItem> getAllCartItem(String userId) throws DataAccessException;
//	CartItem getCartItem(String cartItemID) throws DataAccessException;

//	int getUnitPrice(String cartItemID) throws DataAccessException;
//	int getTotalPrice(String userId) throws DataAccessException;
	
	void insertCartItem(String productId, int quantity, String userId)  throws DataAccessException;
	
	CartItem isInCart(String productId, String userId) throws DataAccessException;
	void increaseQuantity(CartItem cartItem) throws DataAccessException;
//	void deleteCartItem(String cartItemID) throws DataAccessException;
//	void updateQuantity(String cartItemID, int quantity) throws DataAccessException;

}
