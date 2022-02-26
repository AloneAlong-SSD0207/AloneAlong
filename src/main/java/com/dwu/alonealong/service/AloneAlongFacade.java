package com.dwu.alonealong.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.dwu.alonealong.domain.CartItem;
import com.dwu.alonealong.domain.Food;
import com.dwu.alonealong.domain.FoodCart;
import com.dwu.alonealong.domain.FoodOrder;
import com.dwu.alonealong.domain.FoodReview;
import com.dwu.alonealong.domain.Order;
import com.dwu.alonealong.domain.Payment;
import com.dwu.alonealong.domain.Product;
import com.dwu.alonealong.domain.ProductOrder;
import com.dwu.alonealong.domain.ProductReview;
import com.dwu.alonealong.domain.Restaurant;
import com.dwu.alonealong.domain.Together;
import com.dwu.alonealong.domain.TogetherFood;
import com.dwu.alonealong.domain.TogetherMember;
import com.dwu.alonealong.domain.TogetherOrder;
import com.dwu.alonealong.domain.User;


public interface AloneAlongFacade {

	//USER
	User getUserByUserId(String Id) throws DataAccessException;
	User getUserByUserIdAndPassword(String Id, String password);
	void createUser(User user);
	void updateUser(User user);
	void removeUser(String Id);
	List<String> getUserIdList();
	
	//CONTACT
	void insertContact(String contents);
	  
	//PRODUCT
	List<Product> getProductList(int pcId, String sortType);
	List<Product> searchProductList(String keywords);
	Product getProduct(String productId);
	
	//PRODUCT Review
	ProductReview getProductReview(long reviewId, String userId);
	List<ProductReview> getProductReviewList(String productId, String sortType, String userId);
	void insertProductReview(ProductReview productReview);
	void updateProductReview(ProductReview productReview);
	void deleteProductReview(long reviewId);
//	int numOfReviews(String productId);
//	double averageOfReviews(String productId);
	int mostRatingOfReviews(String productId);
	void insertProductReviewRecommend(long reviewId, String userId);
	void deleteProductReviewRecommend(long reviewId, String userId);
	
	//PRODUCT Order
	List<Order> getOrdersByUserId(String userId);
//	List<ProductOrder> getOrdersByProductId(String productId);
  	ProductOrder getProductOrder(String orderId);
	void insertProductOrder(ProductOrder order);
	boolean checkUsersOrder(String userId, String productId);
	
	//CART
	List<CartItem> getAllCartItem(String userId) throws DataAccessException;
	CartItem getCartItem(long cartItemId) throws DataAccessException;
	void insertCartItem(String productId, int quantity, String userId)  throws DataAccessException;
	void deleteAllCartItem(String userId) throws DataAccessException;
	void deleteCartItem(long cartItemId) throws DataAccessException;
	void updateCartItem(CartItem cartItem) throws DataAccessException;
	
	//PAYMENT
	Payment getCard(String Id) throws DataAccessException;
	void createCard(Payment payment);
	void updateCard(Payment payment);
	
	//Restaurant
	void insertRestaurant(Restaurant res);
	void updateRestaurant(Restaurant res);
	void deleteRestaurant(long resId);
	List<Restaurant> getRestaurantList();
	List<Restaurant> getRestaurantListByCategory(String category1, String category2, String sortType);
	List<Restaurant> searchRestaurantList(String keywords);
//
	Restaurant getRestaurantByUserId(String userId);
	Restaurant getRestaurantByResId(long resId);
	
	//Food
	void insertFood(Food food);
	void updateFood(Food food);
	void deleteFood(long foodId);
	List<Food> getFoodListByRestaurant(long resId);
	Food getFood(long foodId);

	void insertFoodOrder(FoodOrder order);
	FoodOrder getFoodOrder(String orderId); 
	List<FoodOrder> getFoodOrdersByUserId(String userId);
	void deleteFoodOrder(String orderId);
	
	public List<FoodReview> getFoodReviewListByResId(long resId, String sortType);
	public void insertFoodReview(FoodReview foodReview);
	void updateAvgRating(int rating, long resId);
	
	//Together
	Together getTogetherByTogId(String togId);
	List<Together> getTogetherList();
	void insertTogether(Together together);
	List<Together> getTogetherListByCategory(String area, String date, String kind, int price, String sex, String age);
	void updateTogether(Together together);
	List<Together> recommandTogetherList(String sex, String address);
	List<Together> getTogetherListByResId(long resId);
	void deleteTogether(String togId);
	List<Together> searchTogetherList(String keyword);
	
	//TogetherFood
	List<TogetherFood> getTogetherFoodListByTogId(String togId);
	void insertTogetherFood(TogetherFood togetherFood);
	void deleteTogetherFood(String togId);
	void updateTogetherFood(TogetherFood togetherFood);
	
	//TogetherMember
	List<TogetherMember> getTogetherMemberListByTogId(String togId);
	void insertTogetherMember(TogetherMember togetherMember);
	void deleteTogetherMember(String togId);
	
	//TogetherOrder 관련
	void insertTogetherOrderInfo(Order order);
	void insertTogetherOrder(TogetherOrder togetherOrder);
	List<TogetherOrder> getTogetherOrderByTogId(String togId);
	void deleteTogetherOrder(String togId);
	
	List<TogetherOrder> getTogetherOrderByUserId(String userId);
	void deleteTogetherOrderInfo(String orderId);
	void updateTogetherOrderInfo(Order order);
	
	void insertFoodOrderForTogetherOrder(FoodOrder foodOrder);
	
}