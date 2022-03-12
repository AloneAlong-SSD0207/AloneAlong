package com.dwu.alonealong.domain;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Base64.Encoder;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.multipart.MultipartFile;

import com.dwu.alonealong.controller.food.FoodForm;
import com.dwu.alonealong.controller.food.RestaurantForm;

public class FoodFunction {

	public static final Double defaultDouble = 0.0;
	public static final int defaultInt = 0;
	
	public static void pagingRestaurantList(List<Restaurant> restaurantList, ModelMap model, int page) {
		int itemsForPage = 6;
		int rangeSize = 3;
		PagedListHolder<Restaurant> pagedRestaurantList = new PagedListHolder<Restaurant>(restaurantList);
        pagedRestaurantList.setPageSize(itemsForPage); //페이지당 보여줄 개수
        pagedRestaurantList.setPage(page - 1); //보여줄 페이지
        model.put("restaurantList", pagedRestaurantList.getPageList());
		model.put("restaurantCount", restaurantList.size());
		
		model.put("page", pagedRestaurantList.getPage() + 1);
		model.put("startPage", (pagedRestaurantList.getPage() / rangeSize) * rangeSize + 1);
		model.put("lastPage", pagedRestaurantList.getPageCount());
		model.put("rangeSize", rangeSize);
	}
	public static void pagingReviewList(List<FoodReview> reviewList, ModelMap model, int page) {
		int itemsForPage = 3;
		int rangeSize = 3;
		PagedListHolder<FoodReview> pagedReviewList = new PagedListHolder<FoodReview>(reviewList);
	    pagedReviewList.setPageSize(itemsForPage);
	    pagedReviewList.setPage(page - 1);
	    model.put("foodReviewList", pagedReviewList.getPageList());
		model.put("foodReviewCount", reviewList.size());
		
		model.put("page", pagedReviewList.getPage() + 1);
		model.put("startPage", (pagedReviewList.getPage() / rangeSize) * rangeSize + 1);
		model.put("lastPage", pagedReviewList.getPageCount());
		model.put("rangeSize", rangeSize);
	}
	public static void encodeImgList(List<?> list){
		Encoder encoder = Base64.getEncoder();
        for(Object obj : list) {  
        	if(obj instanceof Restaurant) {
        		Restaurant res = (Restaurant) obj;
	        	byte[] imagefile = res.getImgFile();
	        	if(imagefile == null)
	        		continue;
	            String encodedString = encoder.encodeToString(imagefile);
	            res.setImg64(encodedString);
        	}
        	else if(obj instanceof Food) {
        		Food food = (Food) obj;
	        	byte[] imagefile = food.getImgFile();
	        	if(imagefile == null)
	        		continue;
	            String encodedString = encoder.encodeToString(imagefile);
	            food.setImg64(encodedString);
        	}
        	else if(obj instanceof FoodCartItem) {
        		FoodCartItem item = (FoodCartItem) obj;
        		item.getFood().setImg64(encoder.encodeToString(item.getFood().getImgFile()));
        	}
        }
	}
	
	
	public static void encodeImg(Restaurant res){
		Encoder encoder = Base64.getEncoder();
		byte[] imagefile;
		String encodedString;
        imagefile = res.getImgFile();
        encodedString = encoder.encodeToString(imagefile);
        res.setImg64(encodedString);
	}
	public static void encodeImg(Food food){
		Encoder encoder = Base64.getEncoder();
		byte[] imagefile;
		String encodedString;
        imagefile = food.getImgFile();
        encodedString = encoder.encodeToString(imagefile);
        food.setImg64(encodedString);
	}
	public static  byte[] getImgFile(FoodForm foodForm) {
		MultipartFile mf = foodForm.getImgFile();
		byte[] img = null;
		try {
			img = mf.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	public static byte[] getImgFile(RestaurantForm resForm) {
		MultipartFile mf = resForm.getImgFile();
		byte[] img = null;
		try {
			img = mf.getBytes();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return img;
	}
	
	
	
}
