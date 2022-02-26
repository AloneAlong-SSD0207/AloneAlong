package com.dwu.alonealong.service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

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
	private TogetherRepository togetherRepository;
	@Autowired
	private TogetherFoodRepository togetherFoodRepository;
	@Autowired
	private TogetherMemberRepository togetherMemberRepository;
	@Autowired
	private TogetherOrderRepository togetherOrderRepository;

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
		List<Order> orderList = orderRepository.findByUserIdAndOrderIdStartingWith(userId, "p");
		for(Order order : orderList){
			ProductOrder po = order.getProductOrder();
			po.setLineItems(productLineItemRepository.findByOrderId(order.getOrderId()));
			order.setProductOrder(po);
		}
		return orderList;
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
				Date date = order.getOrderDate();
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
		Together together = togetherRepository.getByTogetherId(togId);
		String togetherId = together.getTogetherId();
		together.setTogetherFoodList(togetherFoodRepository.findByTogetherId(togetherId));
		together.setTogetherMemberList(togetherMemberRepository.findByTogetherIdOrderByTogetherMemberIdAsc(togetherId));

		return together;
	}
	
	@Override
	public List<Together> getTogetherList() {
		List<Together> togetherList = togetherRepository.findAll();
		for(int i = 0; i < togetherList.size(); i++) {
			String togId = togetherList.get(i).getTogetherId();
			togetherList.get(i).setTogetherFoodList(togetherFoodRepository.findByTogetherId(togId));
			togetherList.get(i).setTogetherMemberList(togetherMemberRepository.findByTogetherIdOrderByTogetherMemberIdAsc(togId));
		}

		return togetherList;
	}
	
	@Override
	public void insertTogether(Together together) {
		String togetherId = String.valueOf(togetherRepository.getTogIdFromSeq());
		together.setTogetherId(togetherId);
		togetherRepository.save(together);
	}

	@Override
	public void updateTogether(Together together) {
		togetherRepository.save(together);
	}

	@Override
	public void deleteTogether(String togId) throws DataAccessException {
		togetherRepository.deleteByTogetherId(togId);
	}
	
	@Override
	public List<Together> getTogetherListByCategory(String area, String date, String kind, int price, String sex, String age) {
		switch(area) {
			case "all" : area = ""; break;
			case "seoul" : area = "서울특별시"; break;
			case "gyenggi" : area = "경기도"; break;
			case "busan" : area = "부산광역시"; break;
			case "incheon" : area = "인천광역시"; break;
			case "deagu" : area = "대구광역시"; break;
			case "deageon" : area = "대전광역시"; break;
			case "guangju" : area = "광주광역시"; break;
			case "ulsan" : area = "울산광역시"; break;
		}

		switch(kind) {
			case "all" : kind = ""; break;
			case "korean" : kind = "한식"; break;
			case "western" : kind = "양식"; break;
			case "japanese" : kind = "일식"; break;
			case "chinese" : kind = "중식"; break;
			case "etc" : kind = "기타"; break;
		}

		switch(sex) {
			case "all" : sex = ""; break;
			case "female" : sex = "여성"; break;
			case "male" : sex = "남성"; break;
		}

		switch(age) {
			case "all" : age = ""; break;
			case "10" : age = "10대"; break;
			case "20" : age = "20대"; break;
			case "30" : age = "30대"; break;
			case "40" : age = "40대"; break;
			case "50" : age = "50대 이상"; break;
		}

		List<Together> togetherList = togetherRepository.findGatheringsByCategory(area, date, kind, price, sex, age);
		for(int i = 0; i < togetherList.size(); i++) {
			String togId = togetherList.get(i).getTogetherId();
			togetherList.get(i).setTogetherFoodList(togetherFoodRepository.findByTogetherId(togId));
			togetherList.get(i).setTogetherMemberList(togetherMemberRepository.findByTogetherIdOrderByTogetherMemberIdAsc(togId));
		}

		return togetherList;
	}
	
	@Override
	public List<Together> recommandTogetherList(String sex, String address) {
		StringTokenizer st = new StringTokenizer(address);
		String addressTag = st.nextToken();

		List<Together> togetherList = togetherRepository.findGatheringsBySexAndAddress(sex, addressTag);
		for(int i = 0; i < togetherList.size(); i++) {
			String togId = togetherList.get(i).getTogetherId();
			togetherList.get(i).setTogetherFoodList(togetherFoodRepository.findByTogetherId(togId));
			togetherList.get(i).setTogetherMemberList(togetherMemberRepository.findByTogetherIdOrderByTogetherMemberIdAsc(togId));
		}

		return togetherList;
	}
	
	@Override
	public List<Together> getTogetherListByResId(String resId) {
		List<Together> togetherList = togetherRepository.findGatheringsByResId(resId);
		for(int i = 0; i < togetherList.size(); i++) {
			String togId = togetherList.get(i).getTogetherId();
			togetherList.get(i).setTogetherFoodList(togetherFoodRepository.findByTogetherId(togId));
			togetherList.get(i).setTogetherMemberList(togetherMemberRepository.findByTogetherIdOrderByTogetherMemberIdAsc(togId));
		}

		return togetherList;
	}
  
  	@Override
	public List<Together> searchTogetherList(String keyword) {
		List<Together> togetherList = togetherRepository.findGatheringsByKeyword(keyword);
		for(int i = 0; i < togetherList.size(); i++) {
			String togId = togetherList.get(i).getTogetherId();
			togetherList.get(i).setTogetherFoodList(togetherFoodRepository.findByTogetherId(togId));
			togetherList.get(i).setTogetherMemberList(togetherMemberRepository.findByTogetherIdOrderByTogetherMemberIdAsc(togId));
		}

		return togetherList;
	}
	
	//TogetherFood
	@Override
	public List<TogetherFood> getTogetherFoodListByTogId(String togId) {
		return togetherFoodRepository.findByTogetherId(togId);
	}
	
	@Override
	public void insertTogetherFood(TogetherFood togetherFood) {
		String togetherFoodId = String.valueOf(togetherFoodRepository.getTogFoodIdFromSeq());
		togetherFood.setTogetherFoodId(togetherFoodId);
		togetherFoodRepository.save(togetherFood);
	}
	
	@Override
	public void deleteTogetherFood(String togId) {togetherFoodRepository.deleteByTogetherId(togId);}
	
	@Override
	public void updateTogetherFood(TogetherFood togetherFood) {togetherFoodRepository.save(togetherFood);}
	
	//TogetherMember
	@Override
	public List<TogetherMember> getTogetherMemberListByTogId(String togId) {
		return togetherMemberRepository.findByTogetherIdOrderByTogetherMemberIdAsc(togId);
	}
	
	@Override
	public void insertTogetherMember(TogetherMember togetherMember) {
		String togetherMemberId = String.valueOf(togetherMemberRepository.getTogMemIdFromSeq());
		togetherMember.setTogetherMemberId(togetherMemberId);
		togetherMemberRepository.save(togetherMember);
	}
	
	@Override
	public void deleteTogetherMember(String togId) {
		togetherMemberRepository.deleteByTogetherId(togId);
	}
	
	//TogetherOrder
	@Override
	public void insertTogetherOrder(TogetherOrder togetherOrder) {
		togetherOrderRepository.save(togetherOrder);
	}
	
	@Override
	public List<TogetherOrder> getTogetherOrderByUserId(String userId) {
		return togetherOrderRepository.findByUserId(userId);
	}

	@Override
	public List<TogetherOrder> getTogetherOrderByTogId(String togId) {
		return togetherOrderRepository.findByTogetherId(togId);
	}

	@Override
	public void deleteTogetherOrder(String togId) {
		togetherOrderRepository.deleteByTogetherId(togId);
	}

	//OrderInfo for Together
	@Override
	public void insertTogetherOrderInfo(Order order) {
		String orderId = "t" + togetherOrderRepository.getTogOrderIdFromSeq();
		order.setOrderId(orderId);

		String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		order.setOrderDate(curDate);

		orderRepository.save(order);
	}

	@Override
	public void deleteTogetherOrderInfo(String orderId) {orderRepository.deleteByOrOrderId(orderId);}

	@Override
	public void updateTogetherOrderInfo(Order order) {
		String curDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
		order.setOrderDate(curDate);

		orderRepository.save(order);
	}

	@Override
	public void insertFoodOrderForTogetherOrder(FoodOrder foodOrder) {
		foodOrderDao.insertFoodOrderForTogetherOrder(foodOrder); //나중에 merge 후 전환 예정
	}
}