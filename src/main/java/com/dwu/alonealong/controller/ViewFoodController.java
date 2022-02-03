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
	public void setAlonealong(AloneAlongFacade alonealong) {
		this.alonealong = alonealong;
	}
	
	//메뉴 탭
	@RequestMapping("/eating/{resId}")
	public String viewFoodListByResId(
			@PathVariable("resId") String resId,
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
		encodeImg(res);
        List<Food> foodList = this.alonealong.getFoodListByRestaurant(resId);
        encodeImgList(foodList);
        
        model.put("foodList", foodList);
        model.put("restaurant", res);
        
		return "restaurant";
	}
	
	//리뷰 탭
	@RequestMapping("/eating/{resId}/RestaurantReview")
	public String viewReviewListByResId(
			@RequestParam(value="page", defaultValue="1") int page, 
			@PathVariable("resId") String resId,
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
		encodeImg(res);
        model.put("restaurant", res);
        
        pagingList(reviewList, model, page);
        
		return "restaurantReview";
	}
	
	//같이먹기 탭
	@RequestMapping("/eating/{resId}/togetherList")
	public String viewTogetherListByResId(
			@PathVariable("resId") String resId,
			@SessionAttribute("sessionFoodCart") FoodCart foodCart,
			ModelMap model
			) throws Exception {
		List<Together> togetherList = this.alonealong.getTogetherListByResId(resId);
		model.put("togetherList", togetherList);
		
		model.put("foodCart", foodCart.getFoodItemList());
		Restaurant res = alonealong.getRestaurantByResId(resId);
		
		model.put("totalPrice", foodCart.getSubTotal());
		System.out.println(foodCart.getFoodItemList().size());
	
        encodeImg(res);
        model.put("restaurant", res);
		
		return "togetherListTab";
	}
	private void encodeImg(Restaurant res){
		Encoder encoder = Base64.getEncoder();
		byte[] imagefile;
		String encodedString;
        imagefile = res.getImgFile();
        encodedString = encoder.encodeToString(imagefile);
        res.setImg64(encodedString);
	}
	private void encodeImgList(List<Food> list){
		Encoder encoder = Base64.getEncoder();
		byte[] imagefile;
		String encodedString;
        for(Food food : list) {
        	imagefile = food.getImgFile();
            encodedString = encoder.encodeToString(imagefile);
            food.setImg64(encodedString);
        }
	}	
	private void pagingList(List<FoodReview> reviewList, ModelMap model, int page) {
		PagedListHolder<FoodReview> pagedReviewList = new PagedListHolder<FoodReview>(reviewList);
        pagedReviewList.setPageSize(3);
        pagedReviewList.setPage(page - 1);
        model.put("foodReviewList", pagedReviewList.getPageList());
		model.put("foodReviewCount", reviewList.size());
		
		model.put("page", pagedReviewList.getPage() + 1);
		model.put("startPage", (pagedReviewList.getPage() / 5) * 5 + 1);
		model.put("lastPage", pagedReviewList.getPageCount());
	}
}
