package com.dwu.alonealong.controller;

import java.util.Base64;
import java.util.List;
import java.util.Base64.Encoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.dwu.alonealong.domain.Food;
import com.dwu.alonealong.domain.FoodCart;
import com.dwu.alonealong.domain.Order;
import com.dwu.alonealong.domain.Restaurant;
import com.dwu.alonealong.domain.Together;
import com.dwu.alonealong.domain.TogetherFood;
import com.dwu.alonealong.domain.TogetherMember;
import com.dwu.alonealong.domain.TogetherOrder;
import com.dwu.alonealong.domain.User;
import com.dwu.alonealong.service.AloneAlongFacade;

@Controller
@SessionAttributes({"retaurantList", "sessionFoodCart"})
public class TogetherRegisterController {
	
	private AloneAlongFacade aloneAlong;
	
	@Autowired
	public void setAlonealong(AloneAlongFacade aloneAlong) {
		this.aloneAlong = aloneAlong;
	}
	
	@RequestMapping("/togetherRegister")
	public String handleRequest(
			HttpServletRequest request,
			ModelMap model) throws Exception {
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		
		if(userSession == null) {
			return "redirect:/login";
		}
		return "together/togetherRegisterForm";
	}
	
	//키워드로 레스토랑 리스트 가져오기
	@RequestMapping("/togetherRegister/searchRestaurant")
	public String searchRestaurant(
			@RequestParam("keywords") String keywords,
			ModelMap model) throws Exception {
		model.addAttribute("sessionFoodCart", new FoodCart()); //카트 초기화
		String resId = null;
		model.put("resId", resId);
		
		List<Restaurant> restaurantList = this.aloneAlong.searchRestaurantList(keywords);
		model.put("keywords", keywords);
		
		getRestaurantsImage(restaurantList); //이미지 가져오기
		model.put("restaurantList", restaurantList);

		return "together/togetherRegisterForm";
	}
	
	//해당 식당의 메뉴 가져오기
	@RequestMapping("/togetherRegister/searchMenu")
	public String selectRestaurant(
			@RequestParam("resId") String resId,
			@SessionAttribute("sessionFoodCart") FoodCart foodCart,
			ModelMap model) throws Exception {
		Restaurant restaurant = aloneAlong.getRestaurantByResId(resId);
		List<Food> foodList = aloneAlong.getFoodListByRestaurant(resId);
		
		model.put("keywords", restaurant.getResName()); //검색창에 레스토랑 이름 세팅하기
		model.put("selectedRes", restaurant);
		
		getFoodsImage(foodList); //음식 이미지 가져오기
		model.addAttribute("foodList", foodList);
		model.put("foodCart", foodCart.getAllFoodCartItems());
		
		return "together/togetherRegisterForm";
	}
	
	//together 등록
	@RequestMapping("/togetherRegister/complete")
	public String insertTogether(
			HttpServletRequest request,
			SessionStatus status,
			@RequestParam("name") String name,
			@RequestParam("headCount") int headCount,
			@RequestParam("sex") String sex,
			@RequestParam("age") String age,
			@RequestParam("date") String date,
			@RequestParam("time") String time,
			@RequestParam("description") String description,
			@RequestParam("resId") String resId,
			@ModelAttribute("sessionFoodCart") FoodCart cart,
			ModelMap model) {
		if(resId == null)
			return "redirect:/togetherRegister";
		
		//together 넣기		
		Together together = new Together("TOG_ID.NEXTVAL", name, headCount, date, time, sex, age, description, resId, Together.GATHERING, cart.getSubTotal() / headCount);
		aloneAlong.insertTogether(together);
		
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		User user = aloneAlong.getUserByUserId(userSession.getUser().getId());
		
		insertFoodsIntoTogether(cart, together); //음식 넣기
		insertHostIntoTogether(user, together); //멤버에 유저 넣기
		insertOrder(together, user); //결제 정보에는 들어가기(결제상태는 아직x)
		
		status.setComplete(); //카트 제거
		
		List<Together> togetherList = aloneAlong.getTogetherList();
		model.addAttribute("togetherList", togetherList);
		
		return "redirect:/together";
	}
	
	public void getRestaurantsImage(List<Restaurant> restaurantList) {
		Encoder encoder = Base64.getEncoder();
        for(Restaurant res : restaurantList) {     	
        	byte[] imagefile = res.getImgFile();
            String encodedString = encoder.encodeToString(imagefile);
            res.setImg64(encodedString);
        }
	}
	
	public void getFoodsImage(List<Food> foodList) {
		Encoder encoder = Base64.getEncoder();
		byte[] imagefile;
		String encodedString;
        for(Food food : foodList) {
        	imagefile = food.getImgFile();
            encodedString = encoder.encodeToString(imagefile);
            food.setImg64(encodedString);
        }
	}
	
	public void insertFoodsIntoTogether(FoodCart cart, Together together) {
		for(int i = 0; i < cart.getFoodItemList().size(); i++) {
			TogetherFood togetherFood = new TogetherFood("TOGFOOD_ID.NEXTVAL", together.getTogetherId(), cart.getFoodItemList().get(i).getFood().getFoodId() , cart.getFoodItemList().get(i).getQuantity());
			aloneAlong.insertTogetherFood(togetherFood);
		}
	}
	
	public void insertHostIntoTogether(User user, Together together) {
		TogetherMember togetherMember = new TogetherMember("TOGMEM_ID.NEXTVAL", user.getId(), together.getTogetherId(), TogetherMember.IS_HOST);
		aloneAlong.insertTogetherMember(togetherMember);
	}
	
	public void insertOrder(Together together, User user) {
		Order order = new Order("ORDER_ID.NEXTVAL", together.getPrice(), "결제 대기중", user.getId(), "결제 대기중", "결제 대기중", "결제 대기중");
		aloneAlong.insertTogetherOrderInfo(order);
		
		TogetherOrder togetherOrder = new TogetherOrder(order.getOrderId(), together.getTogetherId());
		aloneAlong.insertTogetherOrder(togetherOrder);
	}
}
