package com.dwu.alonealong.controller.food;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.dwu.alonealong.controller.UserSession;
import com.dwu.alonealong.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.dwu.alonealong.service.AloneAlongFacade;

@Controller
@SessionAttributes({"sessionFoodCart"})
public class FoodOrderController {
	private AloneAlongFacade aloneAlong;

	@Autowired
	private void setAloneAlong(AloneAlongFacade aloneAlong) {
		this.aloneAlong = aloneAlong;
	}
	
	@RequestMapping("/eating/order")
	private String initNewOrder(HttpServletRequest request,
		@RequestParam(value="resId", required=false) long resId,
		@ModelAttribute("sessionFoodCart") FoodCart cart,
		ModelMap model) throws Exception {
		
		model.addAttribute("foodOrderForm", new FoodOrder());

		//유저 정보 및 결제 정보 받아오기
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		if(userSession == null) {
			//forward로 login의 get 가서 login후 post한 다음 refererURL통해서 order폼으로.
			return "forward:/login";
		}

		User user = aloneAlong.getUserByUserId(userSession.getUser().getId());
		Payment paymentMethod = aloneAlong.getCard(userSession.getUser().getId());

		FoodFunction.encodeImgList(cart.getFoodItemList());
		
		//만약 sessionFoodCart.size가 0이면 order창으로 넘어가지 못하도록.
		model.put("foodCart", cart.getAllFoodCartItems());
		model.put("totalPrice", cart.getSubTotal());
		model.put("resId", resId);
		model.put("user", user);
		model.put("payment", paymentMethod);
		Restaurant res = aloneAlong.getRestaurantByResId(resId);
		model.put("openTime", res.getOpenTime());
		model.put("closeTime", res.getCloseTime());

		return "foodOrder";
	}
	
	@RequestMapping("/eating/order/confirm")
	private String confirmOrder(
			@RequestParam(value="resId", required=false) long resId,
			@SessionAttribute("sessionFoodCart") FoodCart cart,
			@ModelAttribute("foodOrderForm") FoodOrderForm form, 
			HttpServletRequest request, ModelMap model,
			SessionStatus status
			) {
		
		List<FoodCartItem> foodList = cart.getFoodItemList();
		String reserveType = form.getReserveType();
		String visitDate = form.getVisitDate();
		Payment payment = new Payment(form.getCcName(), form.getCcNum(), form.getCcDate(),form.getCcCVC());
		
		//user id
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		User user = aloneAlong.getUserByUserId(userSession.getUser().getId());
		String userId = user.getId();
		
		FoodOrder order = new FoodOrder(resId, userId, foodList, reserveType, visitDate, payment);
		order.setTotalPrice(order.calcTotalPrice());
		order.setFoodId(foodList.get(0).getFood().getFoodId());
		
		aloneAlong.insertFoodOrder(order);

		String resName = aloneAlong.getRestaurantByResId(resId).getResName();
		//status.setComplete();  // remove sessionCart and orderForm from session
		cart = new FoodCart();
		
		model.put("resName", resName);
		return "resOrderResult";
	}
	@RequestMapping("/eating/order/delete")
	private String deleteOrder(
			@RequestParam(value="orderId") String orderId, 
			Model model
			) {
		
		aloneAlong.deleteFoodOrder(orderId);

		return "redirect:/myResOrder";
	}

}