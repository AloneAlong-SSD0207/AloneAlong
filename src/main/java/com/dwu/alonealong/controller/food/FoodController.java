package com.dwu.alonealong.controller.food;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.dwu.alonealong.domain.Food;
import com.dwu.alonealong.domain.FoodFunction;
import com.dwu.alonealong.service.AloneAlongFacade;
import com.dwu.alonealong.service.FoodFormValidator;

@Controller
@RequestMapping("/eating/{resId}/adminFood")
public class FoodController {
	
	private AloneAlongFacade alonealong;
	
	@Autowired
	private void setAlonealong(AloneAlongFacade alonealong) {
		this.alonealong = alonealong;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	private String insertFoodForm(
			@ModelAttribute("food") FoodForm foodForm, 
			@PathVariable("resId") String resId) {
		return "foodForm";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	private String insertFood(
			@ModelAttribute("food") FoodForm foodForm,
			BindingResult result,
			@PathVariable("resId") long resId,
			Model model) {
		
		new FoodFormValidator().validate(foodForm, result);
		if (result.hasErrors()) { 
			return "foodForm"; 
		}
		
		Food food = new Food();
		food.setResId(foodForm.getResId());
		food.setPrice(foodForm.getPrice());
		food.setName(foodForm.getName());
		food.setDescription(foodForm.getDescription());
		food.setImgFile(FoodFunction.getImgFile(foodForm));
		food.setMaxPeopleNum(food.getMaxPeopleNum());
		//(resId, foodForm.getPrice(), foodForm.getName(),  foodForm.getDescription(), FoodFunction.getImgFile(foodForm), foodForm.getMaxPeopleNum());
		alonealong.insertFood(food);
		
		List<Food> foodList = alonealong.getFoodListByRestaurant(resId);
		model.addAttribute("foodList", foodList);
		
		return "redirect:/eating/{resId}";
	}
	
	@RequestMapping(value = "/update", method = RequestMethod.GET) 
	private String updateFoodForm(
			@RequestParam("foodId") long foodId,
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
	private String updateFood(
			@RequestParam("foodId") long foodId,
			@ModelAttribute("food") FoodForm foodForm,
			@PathVariable("resId") long resId,
			BindingResult result,
			Model model) {
		
		new FoodFormValidator().validate(foodForm, result); 
		if (result.hasErrors()) { 
			return "foodForm"; 
		}
		
		Food food = new Food();
		food.setFoodId(foodId);
		food.setResId(resId);
		food.setName(foodForm.getName());
		food.setPrice(foodForm.getPrice());
		food.setDescription(foodForm.getDescription());
		food.setMaxPeopleNum(foodForm.getMaxPeopleNum());
		food.setImgFile(FoodFunction.getImgFile(foodForm));
		if(food.getImgFile().length == 0){
			Food origin = alonealong.getFood(foodId);
			food.setImgFile(origin.getImgFile());
		}
		alonealong.updateFood(food);
		
		List<Food> foodList = alonealong.getFoodListByRestaurant(resId);
		model.addAttribute("foodList", foodList);
		return "redirect:/eating/{resId}";
	}
	@RequestMapping(value = "/delete")
	private String deleteFood(
			@RequestParam("foodId") long foodId,
			@PathVariable("resId") long resId,
			Model model) {
		
		alonealong.deleteFood(foodId);
		
		List<Food> foodList = alonealong.getFoodListByRestaurant(resId);
		model.addAttribute("foodList", foodList);
		return "redirect:/eating/{resId}";
	}
	
	
}
