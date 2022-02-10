package com.dwu.alonealong.controller;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dwu.alonealong.domain.FoodFunction;
import com.dwu.alonealong.domain.FoodOrder;
import com.dwu.alonealong.domain.FoodReview;
import com.dwu.alonealong.domain.User;
import com.dwu.alonealong.service.AloneAlongFacade;

@Controller
public class FoodReviewController {
		
	private AloneAlongFacade alonealong;
	
	@Autowired
	public void setAlonealong(AloneAlongFacade alonealong) {
		this.alonealong = alonealong;
	}
		
	@RequestMapping("/eating/{orderId}/writeReview")
	@Transactional
	public String insertReview(
			@PathVariable("orderId") String orderId,
			HttpServletRequest request,
			Model model) {
	
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		User user = alonealong.getUserByUserId(userSession.getUser().getId());
		
		String resId = alonealong.getFoodOrder(orderId).getResId();
		String userId = user.getId(); 
		int rating = Integer.parseInt(request.getParameter("rating"));
		String contents = request.getParameter("review");
		FoodReview foodReview = new FoodReview(orderId, resId, userId, rating, contents, FoodFunction.defaultInt);

		alonealong.insertFoodReview(foodReview);
		alonealong.updateAvgRating(rating, resId);
		
		String url = "redirect:/eating/" + resId + "/RestaurantReview";
		return url;

	}
	
//	@RequestMapping(value = "/delete")
//	public String delete(
//			@RequestParam("foodId") String foodId,
//			@PathVariable("resId") String resId,
////			BindingResult bindingResult,
//			Model model) {
//		
//		alonealong.deleteFood(foodId);
//		
//		List<Food> foodList = alonealong.getFoodListByRestaurant(resId);
//		model.addAttribute("foodList", foodList);
//		return "redirect:/eating/{resId}";
//	}
	
	
}
