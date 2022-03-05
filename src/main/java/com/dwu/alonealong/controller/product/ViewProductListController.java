package com.dwu.alonealong.controller.product;

import java.util.Arrays;
import java.util.List;

import com.dwu.alonealong.exception.StockException;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.dwu.alonealong.domain.Product;

@Controller
public class ViewProductListController {
	private static final int PAGE_SIZE = 15;
	private static final int UNIT_PAGE_SIZE = 5;
	private static final int PC_SIZE = 5;
	String[] productCategory = {"소량 과일", "소량 채소", "소량 육류", "소량 식재료", "밀키트"};

	RestTemplate restTemplate = new RestTemplate();

	@RequestMapping("/shop")
	public String handleRequest(@RequestParam(value="page", defaultValue="1") int page, 
			@RequestParam(value="pcId",  defaultValue="1") int pcId, 
			@RequestParam(value="sortType",  defaultValue="new") String sortType, //new, past, sales, lowPrice,
			ModelMap model) throws Exception {
		if(pcId <= 0 || pcId > PC_SIZE) {
			throw new Exception("존재하지 않는 카테고리입니다.");
		}
		Product[] productObject = restTemplate.getForObject("http://localhost:8080/shops/" + pcId + "?sort=" + sortType, Product[].class);
		List<Product> productList = Arrays.asList(productObject);
		PagedListHolder<Product> productPagedList = new PagedListHolder<Product>(productList);
		productPagedList.setPageSize(PAGE_SIZE);
		productPagedList.setPage(page - 1);
		
		String sortTypeName = "최신순";
		switch(sortType) {
			case "past" : sortTypeName = "과거순"; break;
			case "sales" : sortTypeName = "인기순"; break;
			case "lowPrice" : sortTypeName = "낮은 가격순"; break;
		}
		model.put("pcId", pcId);
		model.put("pcList", productCategory);
		model.put("sortTypeName", sortTypeName);
		model.put("productList", productPagedList);
		model.put("productCount", productList.size());
		
		model.put("page", productPagedList.getPage() + 1);
		model.put("startPage", (productPagedList.getPage() / UNIT_PAGE_SIZE) * UNIT_PAGE_SIZE + 1);
		model.put("lastPage", productPagedList.getPageCount());
		return "productList";
	}
}