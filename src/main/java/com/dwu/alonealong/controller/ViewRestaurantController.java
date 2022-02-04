package com.dwu.alonealong.controller;

import java.util.Base64;
import java.util.List;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dwu.alonealong.domain.FoodCart;
import com.dwu.alonealong.domain.Restaurant;
import com.dwu.alonealong.service.AloneAlongFacade;


@Controller
@SessionAttributes({"sessionFoodCart", "category1", "category2", "userSession"})
public class ViewRestaurantController {
	private AloneAlongFacade alonealong;
	
	@Autowired
	public void setAlonealong(AloneAlongFacade alonealong) {
		this.alonealong = alonealong;
	}

	@RequestMapping("/eating")
	public String viewRestaurantWithSort(
			@RequestParam(value="page", defaultValue="1") int page, 
			@RequestParam(value="category1",  defaultValue="지역") String category1,
			@RequestParam(value="category2",  defaultValue="분류") String category2,
			@RequestParam(value="sortType",  defaultValue="new") String sortType,
			ModelMap model
			) throws Exception {
		
		model.addAttribute("sessionFoodCart", new FoodCart()); //식당에서 나올 때마다 카트 초기화.

		String sortTypeQuery = "";	
		String sortTypeName = "";
		if(sortType.equals("new")) {
			sortTypeName = "최신 등록순";
			sortTypeQuery = "RES_DATE";
		}
		if(sortType.equals("review")) {
			sortTypeName = "리뷰 많은순"; 
			sortTypeQuery = "REV_COUNT";
		}
		if(sortType.equals("rating")) {
			sortTypeName = "별점 높은순";
			sortTypeQuery = "AVG_RATING";
		}
			
		List<Restaurant> restaurantList = alonealong.getRestaurantListByCategory(category1, category2, sortTypeQuery);
		model.put("sortTypeName", sortTypeName);
		model.put("category1", category1);
		model.put("category2", category2);

		encodeImgList(restaurantList);
		pagingList(restaurantList, model, page);
		
		return "restaurantList";
	}
	private void pagingList(List<Restaurant> restaurantList, ModelMap model, int page) {
		PagedListHolder<Restaurant> pagedRestaurantList = new PagedListHolder<Restaurant>(restaurantList);
        pagedRestaurantList.setPageSize(6);
        pagedRestaurantList.setPage(page - 1);
        model.put("restaurantList", pagedRestaurantList.getPageList());
		model.put("restaurantCount", restaurantList.size());
		
		model.put("page", pagedRestaurantList.getPage() + 1);
		model.put("startPage", (pagedRestaurantList.getPage() / 5) * 5 + 1);
		model.put("lastPage", pagedRestaurantList.getPageCount());
	}
	private void encodeImgList(List<Restaurant> list){
		Encoder encoder = Base64.getEncoder();
        for(Restaurant res : list) {     	
        	byte[] imagefile = res.getImgFile();
        	if(imagefile == null)
        		continue;
            String encodedString = encoder.encodeToString(imagefile);
            res.setImg64(encodedString);
        }
	}
}
