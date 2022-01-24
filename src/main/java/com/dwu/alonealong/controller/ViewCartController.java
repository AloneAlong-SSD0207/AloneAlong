package com.dwu.alonealong.controller;

import java.util.Base64;
import java.util.List;
import java.util.Base64.Encoder;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dwu.alonealong.domain.CartItem;
import com.dwu.alonealong.service.AloneAlongFacade;

@Controller
@SessionAttributes({"userSession"})
public class ViewCartController {
	private AloneAlongFacade aloneAlong;

	@Autowired
	public void setAloneAlong(AloneAlongFacade aloneAlong) {
		this.aloneAlong = aloneAlong;
	}
	
	@RequestMapping("/cart")
	public String handleRequest(HttpServletRequest request,
			@RequestParam(value="productId", required=false) String productId,
			@RequestParam(value="stockError", required=false) boolean stockError,  
			ModelMap model) throws Exception {
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		if(userSession == null) {
			return "forward:/login";
		}
		String userId = userSession.getUser().getId();
		
		int totalPrice = 0;
		int shippingFee = 0;
		List<CartItem> cart = this.aloneAlong.getAllCartItem(userId);
		for(CartItem cartItem : cart) {
			totalPrice += cartItem.getUnitPrice();
		}

		model.put("productsPrice", totalPrice);
		if (totalPrice != 0 && totalPrice < 30000) {
			shippingFee = 3000;
			totalPrice += shippingFee;
		}
		model.put("shippingFee", shippingFee);
		model.put("totalPrice", totalPrice);
		model.put("cart", cart);

        if(stockError == true) {
    		model.put("cartItemName", aloneAlong.getProduct(productId).getProductName());
        }
		return "thyme/Cart";
//		return "productCart";
	}

}