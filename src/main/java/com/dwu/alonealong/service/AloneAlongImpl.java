package com.dwu.alonealong.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.dwu.alonealong.domain.*;
import com.dwu.alonealong.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dwu.alonealong.dao.UserDAO;
import com.dwu.alonealong.dao.FoodDAO;
import com.dwu.alonealong.dao.FoodLineItemDAO;
import com.dwu.alonealong.dao.FoodOrderDAO;
import com.dwu.alonealong.dao.FoodReviewDAO;
import com.dwu.alonealong.dao.OrderInfoDAO;
import com.dwu.alonealong.dao.PaymentDAO;
import com.dwu.alonealong.dao.RestaurantDAO;
import com.dwu.alonealong.dao.TogetherDAO;
import com.dwu.alonealong.dao.TogetherFoodDAO;
import com.dwu.alonealong.dao.TogetherMemberDAO;
import com.dwu.alonealong.dao.TogetherOrderDAO;
import com.dwu.alonealong.domain.CartItem;
import com.dwu.alonealong.domain.Food;
import com.dwu.alonealong.domain.FoodCartItem;
import com.dwu.alonealong.domain.FoodLineItem;
import com.dwu.alonealong.domain.FoodOrder;
import com.dwu.alonealong.domain.FoodReview;
import com.dwu.alonealong.domain.Order;
import com.dwu.alonealong.domain.Payment;
import com.dwu.alonealong.domain.Product;
import com.dwu.alonealong.domain.ProductOrder;
import com.dwu.alonealong.domain.ProductReview;
import com.dwu.alonealong.domain.Restaurant;
import com.dwu.alonealong.domain.RestaurantRepository;
import com.dwu.alonealong.domain.Together;
import com.dwu.alonealong.domain.TogetherFood;
import com.dwu.alonealong.domain.TogetherMember;
import com.dwu.alonealong.domain.TogetherOrder;
import com.dwu.alonealong.domain.User;

@Service
@Transactional
public class AloneAlongImpl implements AloneAlongFacade{
	//restaurant
	@Autowired
	private RestaurantDAO restaurantDao;
	@Autowired
	private RestaurantRepository restaurantRepository;
	@Autowired
	private FoodDAO foodDao;
	@Autowired
	private FoodRepository foodRepository;
	@Autowired
	private FoodLineItemDAO foodLineItemDao;
	@Autowired
	private FoodLineItemRepository foodLineItemRepository;
	@Autowired
	private FoodOrderDAO foodOrderDao;
	@Autowired
	private FoodOrderRepository foodOrderRepository;
	@Autowired
	private OrderInfoDAO orderInfoDao;
	@Autowired
	private FoodOrderInfoRepository foodOrderInfoRepository;
	@Autowired
	private FoodReviewDAO foodReviewDao;
	@Autowired
	private FoodReviewRepository foodReviewRepository;
	
	
    @Autowired
	private UserDAO userDao;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private ProductReviewRepository pReviewRepository;
	@Autowired
	private ProductReviewRecommendRepository prRecommendRepository;
	@Autowired
	private ProductOrderRepository productOrderRepository;
	@Autowired
	private OrderRepository orderRepository;
	@Autowired
	private ProductLineItemRepository productLineItemRepository;

	@Autowired
	private PaymentDAO paymentDao;
	@Autowired
	private CartItemRepository cartItemRepository;
	
	@Autowired
	private TogetherDAO togetherDao;
	@Autowired
	private TogetherFoodDAO togetherFoodDao;
	@Autowired
	private TogetherMemberDAO togetherMemberDao;
	@Autowired
	private TogetherOrderDAO togetherOrderDao;

	//User
	public User getUserByUserId(String Id) throws DataAccessException{
		return userDao.getUserByUserId(Id);
	}
	
	public User getUserByUserIdAndPassword(String Id, String password) throws DataAccessException{
		return userDao.getUserByUserIdAndPassword(Id, password);
	}
	public void createUser(User user) {
		userDao.createUser(user);
		return;
	}
	public void updateUser(User user) {
		userDao.updateUser(user);
		return;
	}
	public void removeUser(String Id) {
		userDao.removeUser(Id);
		return;
	}
	public List<String> getUserIdList(){
		return userDao.getUserIdList();
	}
	
	//Contact
	public void insertContact(String contents) {
		userDao.insertContact(contents);
	}
	
	//Product
	public List<Product> getProductList(int _pcId, String sortType){
		String pcId =  Integer.toString(_pcId);
		switch(sortType) {
			case "past" : return productRepository.findByPcIdOrderByProductDateAsc(pcId);
			case "sales" : return productRepository.findByPcIdOrderByProductSalesDesc(pcId);
			case "lowPrice" : return productRepository.findByPcIdOrderByProductPriceAsc(pcId);
			default: return productRepository.findByPcIdOrderByProductDateDesc(pcId);
		}
	}
	public List<Product> searchProductList(String keywords){
		return productRepository.findByProductNameContaining(keywords);
	}
	public Product getProduct(String productId){
		return productRepository.findByProductId(productId);
	}
	
	//Product Review
	public ProductReview getProductReview(long reviewId, String userId){
		ProductReview review = pReviewRepository.findByReviewId(reviewId);
		review.setCheckRecommend(prRecommendRepository.existsByReviewIdAndUserId(reviewId, userId));
		return review;
	}
	public List<ProductReview> getProductReviewList(String productId, String sortType, String userId){
		Sort sort;
		switch(sortType) {
			case "recommend" : sort = Sort.by(Sort.Direction.DESC, "recommend"); break;
			case "lowRating" : sort = Sort.by(Sort.Direction.ASC, "rating"); break;
			case "highRating" : sort = Sort.by(Sort.Direction.DESC, "rating"); break;
			default: sort = Sort.by(Sort.Direction.DESC, "reviewDate");
		}
		List<ProductReview> reviewList = pReviewRepository.findByProductId(productId, sort);
		for (ProductReview review : reviewList){
			review.setCheckRecommend(prRecommendRepository.existsByReviewIdAndUserId(review.getReviewId(), userId));
		}
		return reviewList;
	}
	public void insertProductReview(ProductReview productReview) {
		pReviewRepository.save(productReview);
	}
	public void updateProductReview(ProductReview productReview) {
		pReviewRepository.save(productReview);
	}
	public void deleteProductReview(long reviewId) {
		prRecommendRepository.deleteByReviewId(reviewId);
		pReviewRepository.deleteByReviewId(reviewId);
	}
	public int mostRatingOfReviews(String productId){
		return pReviewRepository.mostRatingOfReviews(productId).get(0);
	}
	public void insertProductReviewRecommend(long reviewId, String userId){
		ProductReviewRecommend recommend = new ProductReviewRecommend();
		recommend.setReviewId(reviewId);
		recommend.setUserId(userId);
		prRecommendRepository.save(recommend);
		return;
	}
	public void deleteProductReviewRecommend(long reviewId, String userId){
		prRecommendRepository.deleteByReviewIdAndUserId(reviewId, userId);
		return;
	}
	
	//PRODUCT Order
	public List<Order> getOrdersByUserId(String userId){
		List<Order> orderList = orderRepository.findByUserId(userId);
		for(Order order : orderList){
			ProductOrder po = order.getProductOrder();
			po.setLineItems(productLineItemRepository.findByOrderId(order.getOrderId()));
			order.setProductOrder(po);
		}
		return orderRepository.findByUserId(userId);
	}
	public ProductOrder getProductOrder(String orderId){
		return productOrderRepository.findByOrderId(orderId);
	}
	public void insertProductOrder(ProductOrder productOrder){
		String orderId = "p" + productOrderRepository.getOrderId();
		productOrder.setOrderId(orderId);
		productOrder.setShipPhoneByForm();
		productOrder.setOrder(productOrder.getOrder().saveSet(productOrder.getOrder(), orderId));
		productOrderRepository.save(productOrder);
		for(ProductLineItem item : productOrder.getLineItems()){
			item.setOrderId(orderId);
			Product product = item.getProduct();
			product.setProductSales(product.getProductSales() + item.getQuantity());
			product.setProductStock(product.getProductStock() - item.getQuantity());
			productRepository.save(product);
			productLineItemRepository.save(item);
		}
	}
	public boolean checkUsersOrder(String userId, String productId){
		return orderRepository.existsByOrderIdAndUserId(userId, productId);
	}
	
	//cart
	public List<CartItem> getAllCartItem(String userId) throws DataAccessException{
		return cartItemRepository.findByUserId(userId);
	}
	public CartItem getCartItem(long cartItemId) throws DataAccessException{
		return cartItemRepository.findByCartItemId(cartItemId);
	}
	public void insertCartItem(String productId, int quantity, String userId)  throws DataAccessException{
		CartItem cartItem = new CartItem(userId, productId, quantity);
		CartItem existItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
		if(existItem != null){
			cartItem.setCartItemId(existItem.getCartItemId());
			cartItem.setQuantity(quantity + existItem.getQuantity());
		}
		cartItemRepository.save(cartItem);
	}
	public void deleteAllCartItem(String userId) throws DataAccessException{
		cartItemRepository.deleteByUserId(userId);
	}
	public void deleteCartItem(long cartItemId) throws DataAccessException{
		cartItemRepository.deleteByCartItemId(cartItemId);
	}
	public void updateCartItem(CartItem cartItem) throws DataAccessException{
		cartItemRepository.save(cartItem);
	}
	
	//Payment
	public Payment getCard(String Id) throws DataAccessException{
		return paymentDao.getCard(Id);
	}
	public void createCard(Payment payment) {
		paymentDao.createCard(payment);
	}
	public void updateCard(Payment payment) {
		paymentDao.updateCard(payment);
	}

	//Restaurant
	@Override
	public void insertRestaurant(Restaurant res) {
		restaurantRepository.save(res);
	}
	@Override
	public void updateRestaurant(Restaurant res) {
		restaurantRepository.save(res);
	}
	@Override
	public void deleteRestaurant(long resId) {
		//restaurantDao.deleteRestaurant(ownerId);
		restaurantRepository.deleteById(resId);
	}
	@Override
	public List<Restaurant> getRestaurantList() {
		return (List<Restaurant>) restaurantRepository.findAll();
	}
	@Override
	public List<Restaurant> getRestaurantListByCategory(String category1, String category2, String sortType){
		if(category1.equals("지역")) category1 = "";
		if(category2.equals("분류")) category2 = "";
		if(sortType.equals("RES_DATE")) {
			return restaurantRepository.findByAreaContainingAndCategoryIdContainingOrderByResDateDesc(category1, category2);
		}else if(sortType.equals("REV_COUNT")) {
			return restaurantRepository.findByAreaContainingAndCategoryIdContainingOrderByRevCountDesc(category1, category2);
		}else if(sortType.equals("AVG_RATING")) {
			return restaurantRepository.findByAreaContainingAndCategoryIdContainingOrderByAvgRatingDesc(category1, category2);
		}
		return null;
	}
	@Override
	public List<Restaurant> searchRestaurantList(String keywords) {
		return restaurantRepository.findByResNameContainingIgnoreCaseOrResDescriptionContainingIgnoreCase(keywords, keywords);
	}
	@Override
	public Restaurant getRestaurantByUserId(String userId) {
		return restaurantRepository.findByOwnerId(userId);
	}
	@Override
	public Restaurant getRestaurantByResId(long resId) {
		return restaurantRepository.findByResId(resId);
	}


	//Food
	@Override
	public List<Food> getFoodListByRestaurant(long resId) {
		return foodRepository.findByResId(resId);
	}
	@Override
	public void insertFood(Food food) {
		foodRepository.save(food);
	}
	@Override
	public void updateFood(Food food) {
		foodRepository.save(food);
	}
	@Override
	public void deleteFood(long foodId) {
		foodRepository.deleteById(foodId);
	}

	@Override
	public Food getFood(long foodId) {
		return foodRepository.findByFoodId(foodId);
	}

	//Food Order
	@Override
	@Transactional
	public void insertFoodOrder(FoodOrder order) {
		String newOrderId;
		//주문 하나의 주문자정보
		orderInfoDao.insertFoodOrderInfo(order); //foodId는 그냥 맨 처음 대표적인 list[0]로 foodId 넣음.
		//주문 하나의 예약정보
		newOrderId = "fo" + orderInfoDao.getRecentOrderId();
		foodOrderDao.insertFoodOrder(order, newOrderId);


		//카트 item들 모두 넣기
		for(FoodCartItem val : order.getFoodList()) {
			FoodLineItem item = new FoodLineItem(newOrderId, val.getFood().getFoodId(), val.getQuantity(), val.getUnitPrice());
			//foodLineItemRepository.save(item);
			foodLineItemDao.insertFoodLineItem(item);
		}

	}

	@Transactional
	public void deleteFoodOrder(String orderId) {
		foodOrderInfoRepository.deleteById(orderId);
	}
	@Override
	public FoodOrder getFoodOrder(String orderId) {
		return foodOrderRepository.findByOrderId(orderId);
	}
	@Override
	public List<FoodOrder> getFoodOrdersByUserId(String userId) {
		List<Order> orderList = foodOrderInfoRepository.findByUserIdOrderByOrderDateDesc(userId);
		List<FoodOrder> foodOrderList = new ArrayList<>();
		for(Order order : orderList){
			String orderId = order.getOrderId();
			if(orderId.startsWith("fo")){
				System.out.println("foodOrder 아이디 : " + orderId);
				List<FoodLineItem> lineItemList = foodLineItemRepository.findByOrderId(orderId);
				FoodOrder foodOrder = order.getFoodOrder();
				foodOrder.setOrderedList(lineItemList);
				foodOrder.setTotalPrice(order.getTotalPrice());

				SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				Date date = null;
				try {
					date = dt.parse(order.getOrderDate());
				} catch (ParseException e) {
					e.printStackTrace();
				}
				String newstring = new SimpleDateFormat("yy-MM-dd").format(date);


				foodOrder.setOrderDate(newstring);
				for(FoodLineItem foodLineItem : foodOrder.getOrderedList()){
					String foodName = foodRepository.findByFoodId(foodLineItem.getFoodId()).getName();
					foodLineItem.setFoodName(foodName);
				}
				foodOrderList.add(foodOrder);
			}

		}

		return foodOrderList;

	}
	//FoodReview
	public List<FoodReview> getFoodReviewListByResId(long resId, String sortType) {
		if(sortType.equals("REVIEW_DATE DESC")) {
			return foodReviewRepository.findByResIdOrderByReviewDateDesc(resId);
		}else if(sortType.equals("REVIEW_RATING DESC")) {
			return foodReviewRepository.findByResIdOrderByRatingDesc(resId);
		}else if(sortType.equals("REVIEW_RATING")) {
			return foodReviewRepository.findByResIdOrderByRatingAsc(resId);
		}
		return null;
	}

	public void insertFoodReview(FoodReview foodReview) {
		//foodReviewDao.insertFoodReview(foodReview);
		foodReviewRepository.save(foodReview);
	}
	@Override
	public void updateAvgRating(int rating, long resId) {
		Restaurant res = restaurantRepository.findByResId(resId);

		double avg_rating = ((res.getAvgRating() * res.getRevCount() + rating) / (res.getRevCount() + 1));
		res.setAvgRating(avg_rating);
		res.setRevCount(res.getRevCount() + 1);

		restaurantRepository.save(res);
		//restaurantDao.updateAvgRating(rating, resId);
	}
  
	//together
	@Override
	public Together getTogetherByTogId(String togId) {
		return togetherDao.getTogetherByTodId(togId);
	}
	
	@Override
	public List<Together> getTogetherList() {
		return togetherDao.getTogetherList();
	}
	
	@Override
	public void insertTogether(Together together) {
		togetherDao.insertTogether(together);
	}
	
	@Override
	public List<Together> getTogetherListByCategory(String area, String date, String kind, int price, String sex, String age) {
		return togetherDao.getTogetherListByCategory(area, date, kind, price, sex, age);
	}
	
	@Override
	public void updateTogether(Together together) {
		togetherDao.updateTogether(together);
	}
	
	@Override
	public List<Together> recommandTogetherList(String sex, String address) {
		return togetherDao.recommandTogetherList(sex, address);
	}
	
	@Override
	public List<Together> getTogetherListByResId(long resId) {
		return togetherDao.getTogetherListByResId(resId);
	}
	
	@Override
	public void deleteTogether(String togId) throws DataAccessException {
		togetherDao.deleteTogether(togId); 
  }
  
  @Override
	public List<Together> searchTogetherList(String keyword) {
		return togetherDao.searchTogetherList(keyword);
	}
	
	//TogetherFood
	@Override
	public List<TogetherFood> getTogetherFoodListByTogId(String togId) {
		return togetherFoodDao.getTogetherFoodListByTogId(togId);
	}
	
	@Override
	public void insertTogetherFood(TogetherFood togetherFood) {
		togetherFoodDao.insertTogetherFood(togetherFood);
	}
	
	@Override
	public void deleteTogetherFood(String togId) {
		togetherFoodDao.deleteTogetherFood(togId);
	}
	
	@Override
	public void updateTogetherFood(TogetherFood togetherFood) {
		togetherFoodDao.updateTogetherFood(togetherFood);
	}
	
	//TogetherMember
	@Override
	public List<TogetherMember> getTogetherMemberListByTogId(String togId) {
		return togetherMemberDao.getTogetherMemberListByTogId(togId);
	}
	
	@Override
	public void insertTogetherMember(TogetherMember togetherMember) {
		togetherMemberDao.insertTogetherMember(togetherMember);
	}
	
	@Override
	public void deleteTogetherMember(String togId) {
		togetherMemberDao.deleteTogetherMember(togId);
	}
	
	//TogetherOrder
	@Override
	public void insertTogetherOrder(TogetherOrder togetherOrder) {
		togetherOrderDao.insertTogetherOrder(togetherOrder);
	}
	
	@Override
	public void insertTogetherOrderInfo(Order order) {
		orderInfoDao.insertTogetherOrderInfo(order);
	}
	
	@Override
	public void insertFoodOrderForTogetherOrder(FoodOrder foodOrder) {
		foodOrderDao.insertFoodOrderForTogetherOrder(foodOrder);
	}
	
	@Override
	public List<TogetherOrder> getTogetherOrderByUserId(String userId) {
		return togetherOrderDao.getTogetherOrderByUserId(userId);
	}

	@Override
	public List<TogetherOrder> getTogetherOrderByTogId(String togId) {
		// TODO Auto-generated method stub
		return togetherOrderDao.getTogetherOrderByTogId(togId);
	}

	@Override
	public void deleteTogetherOrder(String togId) {
		togetherOrderDao.deleteTogetherOrder(togId);
	}

	@Override
	public void deleteTogetherOrderInfo(String orderId) {
		orderInfoDao.deleteTogetherOrderInfo(orderId);
	}

	@Override
	public void updateTogetherOrderInfo(Order order) {
		orderInfoDao.updateTogetherOrderInfo(order);
	}

}