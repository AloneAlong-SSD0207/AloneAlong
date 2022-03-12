package com.dwu.alonealong.controller;

import java.util.Base64;
import java.util.List;
import java.util.Base64.Encoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dwu.alonealong.domain.Food;
import com.dwu.alonealong.domain.FoodCart;
import com.dwu.alonealong.domain.Restaurant;
import com.dwu.alonealong.service.AloneAlongFacade;

@Controller
@SessionAttributes({"sessionFoodCart"})
public class TogetherFoodCartForRegisterController {

	private AloneAlongFacade alonealong;
	
	@Autowired
	public void setAlonealong(AloneAlongFacade alonealong) {
		this.alonealong = alonealong;
	}
	
	@RequestMapping("/togetherRegister/{resId}/addFoodToCart")
	public String addFoodCart(
			@RequestParam("foodId") long foodId,
			@ModelAttribute("sessionFoodCart") FoodCart cart,
			@PathVariable("resId") long resId,
			ModelMap model
			) throws Exception {

		if (cart.containsFoodId(foodId)) {
			cart.incrementQuantityByFoodId(foodId);
		}
		else {
			Food item = this.alonealong.getFood(foodId);
			cart.addFood(item);
		}
		
		List<Food> foodList = this.alonealong.getFoodListByRestaurant(resId);
		getFoodsImage(foodList);
		
		model.put("foodList", foodList);
		model.put("foodCart", cart.getAllFoodCartItems());
		model.put("totalPrice", cart.getSubTotal());
		
		Restaurant res = alonealong.getRestaurantByResId(resId);
		model.put("keywords", res.getResName()); //검색창에 레스토랑 이름 세팅하기
		model.put("selectedRes", res); //선택된 레스토랑
		
		return "together/togetherRegisterForm";
	}
	
	@RequestMapping("/togetherRegister/{resId}/updateFoodCartItem")
	public String updateFoodCart(
			HttpServletRequest request,	
			@ModelAttribute("sessionFoodCart") FoodCart cart,
			@PathVariable("resId") long resId,
			ModelMap model
			) throws Exception {
		
		cart.setQuantityByFoodId(Long.parseLong(request.getParameter("foodId")), Integer.parseInt(request.getParameter("quantity")));
				
		List<Food> foodList = this.alonealong.getFoodListByRestaurant(resId);
		getFoodsImage(foodList);
		
		model.put("foodList", foodList);
		model.put("foodCart", cart.getAllFoodCartItems());
		model.put("totalPrice", cart.getSubTotal());
		
		Restaurant res = alonealong.getRestaurantByResId(resId);
		model.put("keywords", res.getResName()); //검색창에 레스토랑 이름 세팅하기
		model.put("selectedRes", res); //선택된 레스토랑
		
		return "together/togetherRegisterForm";
	}
	
	@RequestMapping("/togetherRegister/{resId}/deleteFoodCartItem")
	public String deleteFoodCart(
			HttpServletRequest request,	
			@ModelAttribute("sessionFoodCart") FoodCart cart,
			@PathVariable("resId") long resId,
			ModelMap model
			) throws Exception {
		
		cart.removeFoodById(Long.parseLong((request.getParameter("foodId"))));
		
		List<Food> foodList = this.alonealong.getFoodListByRestaurant(resId);
		getFoodsImage(foodList);
		
		model.put("foodList", foodList);
		model.put("foodCart", cart.getAllFoodCartItems());
		model.put("totalPrice", cart.getSubTotal());
		
		Restaurant res = alonealong.getRestaurantByResId(resId);
		model.put("keywords", res.getResName()); //검색창에 레스토랑 이름 세팅하기
		model.put("selectedRes", res); //선택된 레스토랑
		
		return "together/togetherRegisterForm";
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
	
}
