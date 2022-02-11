package com.dwu.alonealong.controller.product;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Base64.Encoder;

import javax.servlet.http.HttpServletRequest;

import com.dwu.alonealong.controller.UserSession;
import com.dwu.alonealong.exception.NullProductException;
import com.dwu.alonealong.exception.StockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.dwu.alonealong.domain.User;
import com.dwu.alonealong.domain.Payment;
import com.dwu.alonealong.domain.CartItem;
import com.dwu.alonealong.domain.Product;
import com.dwu.alonealong.domain.ProductLineItem;
import com.dwu.alonealong.domain.ProductOrder;
import com.dwu.alonealong.service.AloneAlongFacade;
import com.dwu.alonealong.service.ProductOrderFormValidator;

@Controller
@SessionAttributes({"productOrderSession"})
public class ProductOrderController {
	private AloneAlongFacade aloneAlong;

	@Autowired
	public void setAloneAlong(AloneAlongFacade aloneAlong) {
		this.aloneAlong = aloneAlong;
	}
	
	@Autowired
	private ProductOrderFormValidator validator;

	public void setValidator(ProductOrderFormValidator validator) {
		this.validator = validator;
	}

	@RequestMapping("/shop/order")
	public String initNewOrder(HttpServletRequest request,
		@RequestParam(value="type") String type,
		@RequestParam(value="productId", required=false) String productId,
		@RequestParam(value="quantity", defaultValue="-1") int quantity,
		@ModelAttribute("productOrderForm") ProductOrderForm productOrderForm,
		ModelMap model) throws Exception {

		String userId = UserSession.getUserId(request);
		User user = aloneAlong.getUserByUserId(userId);
		Payment paymentMethod = aloneAlong.getCard(userId);

		List<ProductLineItem> orderList = new ArrayList<ProductLineItem>();
		int totalPrice = 0;

		if(type.equals("cart")){
			List<CartItem> cart = aloneAlong.getAllCartItem(userId);
			for(CartItem cartItem : cart){
				Product product = this.aloneAlong.getProduct(cartItem.getProductId());
				int stock = product.getProductStock();
				if(stock < quantity){
					throw new StockException();
				}
				orderList.add(new ProductLineItem(cartItem));
				totalPrice += cartItem.getUnitPrice();
			}
			if(totalPrice < Product.FREE_SHIPPING_PRICE) {
				totalPrice += Product.SHIPPING_FEE;
			}
		}
		else if (type.equals("product")){
			if(productId == null || quantity < 0) {
				throw new NullProductException();
			}
			Product product = this.aloneAlong.getProduct(productId);
			product.setQuantity(quantity);

			int stock = product.getProductStock();
			if(stock < quantity){
				throw new StockException();
			}
			totalPrice = product.getUnitPrice();
			ProductLineItem orderItem = new ProductLineItem(product);
			orderList.add(orderItem);
		}
		else {
			return "error";
		}

		productOrderForm = new ProductOrderForm();
		productOrderForm.initProductOrder(user, paymentMethod);
		model.put("orderForm", productOrderForm);
		model.put("productOrderSession", orderList);
		model.put("totalPrice", totalPrice);
		model.put("type", type);
		return "productOrder";
	}
	
	@RequestMapping("/shop/order/confirm")
	protected String confirmOrder(HttpServletRequest request,
			@ModelAttribute("productOrderForm") ProductOrderForm productOrderForm, 
			@SessionAttribute("productOrderSession") List<ProductLineItem> lineItems,
			BindingResult result, ModelMap model, 
			SessionStatus status) throws Exception {
		validator.validate(productOrderForm, result);
		ProductOrder order = productOrderForm.getOrder();

		//유저 정보 및 결제 정보 받아오기
		String userId = UserSession.getUserId(request);
		User user = aloneAlong.getUserByUserId(userId);
		
		//totalPrice
		int totalPrice = 0;
		for (ProductLineItem item : lineItems) {
			totalPrice += item.getUnitPrice();
		}
		if (totalPrice < Product.FREE_SHIPPING_PRICE) {
			totalPrice += Product.SHIPPING_FEE;
		}
				
		if (result.hasErrors()) {
			Payment paymentMethod = aloneAlong.getCard(userId);
			productOrderForm.setOrderUser(user);
			productOrderForm.setPayment(paymentMethod);
			model.put("orderForm", productOrderForm);
			model.put("totalPrice", totalPrice);
			model.put("type", productOrderForm.getType());
			return "productOrder";
		}
		
		//lineItem
		order.setLineItems(lineItems);
		order.setTotalPrice(totalPrice);
		
		//user
		order.setUserId(userId);		
		aloneAlong.insertProductOrder(order);

		if(productOrderForm.getType().equals("cart")) {
			aloneAlong.deleteAllCartItem(userId);
		}
		status.setComplete();  // remove sessionCart and orderForm from session
		return "productOrderResult";
	}

}