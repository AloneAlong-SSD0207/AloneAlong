package com.dwu.alonealong.dao;

import java.util.List;

import com.dwu.alonealong.domain.FoodOrder;

public interface FoodOrderDAO {
	
	List<FoodOrder> getFoodOrdersByUsername(String username);
	
	FoodOrder getFoodOrder(String orderId);
	
	void insertFoodOrder(FoodOrder foodOrder, String orderId);
	
	void insertFoodOrderForTogetherOrder(FoodOrder foodOrder); //추가함
	
	
	//void insertOrderStatus(FoodOrder foodOrder);
	//int mySqlServerInsertOrder(FoodOrder order);
}
