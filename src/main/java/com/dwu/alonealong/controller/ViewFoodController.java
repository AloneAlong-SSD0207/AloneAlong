package com.dwu.alonealong.controller;

import java.util.Base64;
import java.util.Base64.*;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dwu.alonealong.domain.Food;
import com.dwu.alonealong.domain.FoodCart;
import com.dwu.alonealong.domain.FoodFunction;
import com.dwu.alonealong.domain.FoodReview;
import com.dwu.alonealong.domain.Restaurant;
import com.dwu.alonealong.domain.Together;
import com.dwu.alonealong.domain.User;
import com.dwu.alonealong.service.AloneAlongFacade;

@Controller
@SessionAttributes({"sessionFoodCart", "pagedReviewList"})
public class ViewFoodController {
	private AloneAlongFacade alonealong;
		
	@Autowired
	private void setAlonealong(AloneAlongFacade alonealong) {
		this.alonealong = alonealong;
	}
	
	//메뉴 탭
	@RequestMapping("/eating/{resId}")
	private String viewFoodListByResId(
			@PathVariable("resId") long resId,
			@SessionAttribute(value="sessionFoodCart", required=false) FoodCart foodCart,
			HttpServletRequest request,
			ModelMap model) throws Exception {

		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		if(userSession != null) {
			User user = alonealong.getUserByUserId(userSession.getUser().getId());
			String userId = user.getId();
			model.put("userId", userId);
			
		}	 
		
		if(foodCart == null) {
			model.put("category1", "지역");
			model.put("category2", "분류");
			foodCart = new FoodCart();
			model.addAttribute("sessionFoodCart", foodCart);
		}
		model.put("foodCart", foodCart.getFoodItemList());
		model.put("totalPrice", foodCart.getSubTotal());
		
		Restaurant res = alonealong.getRestaurantByResId(resId);
		FoodFunction.encodeImg(res);
        List<Food> foodList = this.alonealong.getFoodListByRestaurant(resId);
        FoodFunction.encodeImgList(foodList);
        
        model.put("foodList", foodList);
        model.put("restaurant", res);
        
		return "restaurant";
	}
	
	//리뷰 탭
	@RequestMapping("/eating/{resId}/RestaurantReview")
	private String viewReviewListByResId(
			@RequestParam(value="page", defaultValue="1") int page, 
			@PathVariable("resId") long resId,
			@SessionAttribute("sessionFoodCart") FoodCart foodCart,
			@RequestParam(value="sortType", defaultValue="REVIEW_DATE DESC") String sortType, 
			HttpServletRequest request,
			ModelMap model) throws Exception {
		
		if(sortType == null)
			sortType = "REVIEW_DATE DESC";
	
		String sortTypeName = "";
		switch(sortType) {
			case "REVIEW_DATE DESC":
				sortTypeName = "최신 등록순";
				break;
			case "REVIEW_RATING DESC":
				sortTypeName = "높은 평점순";
				break;
			case "REVIEW_RATING":
				sortTypeName = "낮은 평점순";
				break;
		}
		
		model.put("sortTypeName", sortTypeName);
		
		List<FoodReview> reviewList = this.alonealong.getFoodReviewListByResId(resId, sortType);
		for(FoodReview review : reviewList) {
			User user = alonealong.getUserByUserId(review.getUserId());
			review.setUserNickName(user.getNickname());
		}
				
		model.put("foodCart", foodCart.getFoodItemList());
		model.put("totalPrice", foodCart.getSubTotal());
		
		Restaurant res = alonealong.getRestaurantByResId(resId);
		FoodFunction.encodeImg(res);
        model.put("restaurant", res);
        
        FoodFunction.pagingReviewList(reviewList, model, page);
        
		return "restaurantReview";
	}
	
	//같이먹기 탭
	@RequestMapping("/eating/{resId}/togetherList")
	private String viewTogetherListByResId(
			@PathVariable("resId") long resId,
			@SessionAttribute("sessionFoodCart") FoodCart foodCart,
			ModelMap model
			) throws Exception {
		List<Together> togetherList = this.alonealong.getTogetherListByResId(resId);
		model.put("togetherList", togetherList);
		
		model.put("foodCart", foodCart.getFoodItemList());
		Restaurant res = alonealong.getRestaurantByResId(resId);
		
		model.put("totalPrice", foodCart.getSubTotal());
		System.out.println(foodCart.getFoodItemList().size());
	
		FoodFunction.encodeImg(res);
        model.put("restaurant", res);
		
		return "togetherListTab";
	}
	
	
}
