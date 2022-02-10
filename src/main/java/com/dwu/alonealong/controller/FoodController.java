package com.dwu.alonealong.controller;

import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.Base64.Encoder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.dwu.alonealong.domain.Food;
import com.dwu.alonealong.domain.FoodFunction;
import com.dwu.alonealong.service.AloneAlongFacade;
import com.dwu.alonealong.service.FoodFormValidator;

@Controller
@RequestMapping("/eating/{resId}/adminFood")
public class FoodController {
	
	private AloneAlongFacade alonealong;
	
	@Autowired
	public void setAlonealong(AloneAlongFacade alonealong) {
		this.alonealong = alonealong;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String insertFoodForm(
			@ModelAttribute("food") FoodForm foodForm, 
			@PathVariable("resId") String resId) {
		return "foodForm";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String insertFood(
			@ModelAttribute("food") FoodForm foodForm,
			BindingResult result,
			@PathVariable("resId") String resId,
			Model model) {
		
		new FoodFormValidator().validate(foodForm, result);
		if (result.hasErrors()) { 
			return "foodForm"; 
		}
		
		Food food = new Food(resId, "FOOD_ID.NEXTVAL", foodForm.getName(), foodForm.getPrice(), foodForm.getDescription(), FoodFunction.getImgFile(foodForm), foodForm.getMaxPeopleNum());
		alonealong.insertFood(food);
		
		List<Food> foodList = alonealong.getFoodListByRestaurant(resId);
		model.addAttribute("foodList", foodList);
		
		return "redirect:/eating/{resId}";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET) 
	public String updateFoodForm(
			@RequestParam("foodId") String foodId,
			@ModelAttribute("food") FoodForm foodForm, 
			@PathVariable("resId") String resId,
			Model model) {
		System.out.println("update");
		Food foodData = alonealong.getFood(foodId);
		
		FoodFunction.encodeImg(foodData); //이미지 변경을 하지 않을 경우 기존의 이미지값을 저장하고있음.
		
		model.addAttribute("food", foodData);
		return "foodForm";
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String updateFood(
			@RequestParam("foodId") String foodId,
			@ModelAttribute("food") FoodForm foodForm,
			@PathVariable("resId") String resId,
			BindingResult result,
			Model model) {
		
		new FoodFormValidator().validate(foodForm, result); 
		if (result.hasErrors()) { 
			return "foodForm"; 
		}
		
		Food food;
		if(foodForm.getImgFile() == null)
			food = new Food(resId, foodId, foodForm.getName(), foodForm.getPrice(), foodForm.getDescription(), null, foodForm.getMaxPeopleNum());
		else {
			food = new Food(resId, foodId, foodForm.getName(), foodForm.getPrice(), foodForm.getDescription(), FoodFunction.getImgFile(foodForm), foodForm.getMaxPeopleNum());
		}
			
		alonealong.updateFood(food);
		
		List<Food> foodList = alonealong.getFoodListByRestaurant(resId);
		model.addAttribute("foodList", foodList);
		return "redirect:/eating/{resId}";
	}
	@RequestMapping(value = "/delete")
	public String deleteFood(
			@RequestParam("foodId") String foodId,
			@PathVariable("resId") String resId,
			Model model) {
		
		alonealong.deleteFood(foodId);
		
		List<Food> foodList = alonealong.getFoodListByRestaurant(resId);
		model.addAttribute("foodList", foodList);
		return "redirect:/eating/{resId}";
	}
	
	
}
