package com.dwu.alonealong.controller;

import com.dwu.alonealong.exception.NullProductException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dwu.alonealong.domain.Product;
import com.dwu.alonealong.service.AloneAlongFacade;

@Controller
@SessionAttributes({"product"})
public class ViewProductController {
	private AloneAlongFacade aloneAlong;

	@Autowired
	public void setAloneAlong(AloneAlongFacade aloneAlong) {
		this.aloneAlong = aloneAlong;
	}
	
	@RequestMapping("/shop/{productId}")
	public String handleRequest(@PathVariable("productId") String productId,
			@RequestParam(value="quantity",  defaultValue="1") int quantity,
			ModelMap model) throws Exception {
		Product product = this.aloneAlong.getProduct(productId);
		if(product == null) { throw new NullProductException(); }

		int stock = product.getProductStock();
		if(stock < quantity) {
			return "redirect:/shop/{productId}/stockError";
		}
		product.setQuantity(quantity);
		model.put("product", product);
		model.put("pcId", product.getPcId());
		return "product";
	}

	@RequestMapping("/shop/{productId}/stockError")
	public String stockError(@PathVariable("productId") String productId,
								ModelMap model) throws Exception {
		Product product = this.aloneAlong.getProduct(productId);
		if(product == null) { throw new NullProductException(); }

		product.setQuantity(1);
		model.put("product", product);
		model.put("stockError", true);
		model.put("pcId", product.getPcId());
		return "product";
	}
}