package com.dwu.alonealong.controller.product;

import javax.servlet.http.HttpServletRequest;

import com.dwu.alonealong.controller.UserSession;
import com.dwu.alonealong.domain.ProductReview;
import com.dwu.alonealong.exception.NullProductException;
import com.dwu.alonealong.exception.StockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dwu.alonealong.domain.CartItem;
import com.dwu.alonealong.domain.Product;
import com.dwu.alonealong.service.AloneAlongFacade;
import com.dwu.alonealong.exception.UserNotMatchException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Controller
@SessionAttributes({"userSession"})
public class CartController {
	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping("/cart")
	public String viewCart(HttpServletRequest request,
								@RequestParam(value="productId", required=false) String productId,
								ModelMap model) throws Exception {
		String userId = UserSession.getUserId(request);

		int totalPrice = 0;
		int shippingFee = 0;
		CartItem[] cartItemList = restTemplate.getForObject("http://localhost:8080/cart/" + userId + "/items", CartItem[].class);
		List<CartItem> cart = Arrays.asList(cartItemList);
		for(CartItem cartItem : cart) {
			totalPrice += cartItem.getUnitPrice();
		}

		model.put("productsPrice", totalPrice);
		if (totalPrice != 0 && totalPrice <  Product.FREE_SHIPPING_PRICE) {
			totalPrice += Product.SHIPPING_FEE;
			shippingFee = Product.SHIPPING_FEE;
		}
		model.put("shippingFee", shippingFee);
		model.put("totalPrice", totalPrice);
		model.put("cart", cart);
		return "thyme/Cart";
	}
}
