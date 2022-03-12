package com.dwu.alonealong.controller.product;

import com.dwu.alonealong.exception.NullProductException;
import com.dwu.alonealong.exception.StockException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.client.RestTemplate;

import com.dwu.alonealong.domain.Product;

@Controller
@SessionAttributes({"product"})
public class ViewProductController {
	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping("/shop/{productId}")
	public String handleRequest(@PathVariable("productId") String productId,
			@RequestParam(value="quantity",  defaultValue="1") int quantity,
			ModelMap model) throws Exception {
		Product product = restTemplate.getForObject("http://localhost:8080/products/" + productId, Product.class);
		if(product == null) {
			throw new NullProductException();
		}
		if(product.getProductStock() < quantity) {
			throw new StockException();
		}
		product.setQuantity(quantity);
		model.put("product", product);
		model.put("pcId", product.getPcId());
		return "product";
	}
}