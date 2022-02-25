package com.dwu.alonealong.controller;

import java.util.List;

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
@SessionAttributes("sessionFoodCart")
public class FoodCartController {

	private AloneAlongFacade alonealong;
	@Autowired
	private void setAlonealong(AloneAlongFacade alonealong) {
		this.alonealong = alonealong;
	}
	
	
	@RequestMapping("/eating/{resId}/addFoodToCart")
	private String addFoodToCart(
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

		putCartSessionData(model, cart, resId);
		return "redirect:/eating/{resId}";
	}
	@RequestMapping("/eating/{resId}/updateFoodCartItem")
	private String updateFoodCartItem(
			HttpServletRequest request,	
			@ModelAttribute("sessionFoodCart") FoodCart cart,
			@PathVariable("resId") long resId,
			ModelMap model
			) throws Exception {
		
		cart.setQuantityByFoodId(request.getParameter("foodId"), Integer.parseInt(request.getParameter("quantity")));
				
		putCartSessionData(model, cart, resId);
		return "redirect:/eating/{resId}";
	}
	@RequestMapping("/eating/{resId}/deleteFoodCartItem")
	private String deleteFoodCartItem(
			HttpServletRequest request,	
			@ModelAttribute("sessionFoodCart") FoodCart cart,
			@PathVariable("resId") long resId,
			ModelMap model
			) throws Exception {
		
		cart.removeFoodById((request.getParameter("foodId")));
				
		putCartSessionData(model, cart, resId);
		return "redirect:/eating/{resId}";
	}
	private void putCartSessionData(ModelMap model, FoodCart cart, long resId) {
		List<Food> foodList = this.alonealong.getFoodListByRestaurant(resId); 
		model.put("foodList", foodList);
		model.put("foodCart", cart.getAllFoodCartItems());
		model.put("totalPrice", cart.getSubTotal());
		Restaurant res = alonealong.getRestaurantByResId(resId);
		model.put("restaurant", res);
	}
	
//	@RequestMapping("/eating/{resId}/updateFoodCartQuantities")
//	public ModelAndView handleRequest(
//			HttpServletRequest request,	
//			@ModelAttribute("sessionCart") FoodCart cart) throws Exception {
//		Iterator<FoodCartItem> cartItems = cart.getAllFoodCartItems();
//		while (cartItems.hasNext()) {
//			FoodCartItem cartItem = (FoodCartItem) cartItems.next();
//			String itemId = cartItem.getFood().getFoodId();
//			try {
//				int quantity = Integer.parseInt(request.getParameter(itemId));
//				cart.setQuantityByFoodId(itemId, quantity);
//				if (quantity < 1) {
//					cartItems.remove();
//				}
//			}
//			catch (NumberFormatException ex) {
//				// ignore on purpose
//			}
//		}
//		return new ModelAndView("restaurant", "foodCart", cart);
//	}

}
