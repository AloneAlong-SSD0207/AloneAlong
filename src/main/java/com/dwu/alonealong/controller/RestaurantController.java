package com.dwu.alonealong.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.dwu.alonealong.domain.FoodFunction;
import com.dwu.alonealong.domain.Restaurant;
import com.dwu.alonealong.domain.User;
import com.dwu.alonealong.service.AloneAlongFacade;
import com.dwu.alonealong.service.RestaurantFormValidator;

@Controller
@RequestMapping("/eating/adminRes")
public class RestaurantController {
	
	private static final String RES_INSERT_FORM = "restaurantForm";
	private AloneAlongFacade alonealong;
	
	@Autowired
	private void setAlonealong(AloneAlongFacade alonealong) {
		this.alonealong = alonealong;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	private String restaurantForm(
			@ModelAttribute("restaurant") RestaurantForm resForm,
			HttpServletRequest request,
			Model model) {
		
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		User user = alonealong.getUserByUserId(userSession.getUser().getId());
		
		Restaurant resData = alonealong.getRestaurantByUserId(user.getId());
		if(resData != null) {
			model.addAttribute("res", resData);
		} 
		return RES_INSERT_FORM;
	}
	
	//insert, update 모두 해결
	@RequestMapping(method = RequestMethod.POST)
	private String insertAndUpdateRestaurant(
			@ModelAttribute("restaurant") RestaurantForm resForm,
			BindingResult result,
			HttpServletRequest request,
			Model model) throws MalformedURLException {
	
		//user id
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		User user = alonealong.getUserByUserId(userSession.getUser().getId());
		String userId = user.getId();
		
		new RestaurantFormValidator().validate(resForm, result); // 검증 실행
		if (result.hasErrors()) { 
			// 검증 오류 발생 시
			Restaurant resData = alonealong.getRestaurantByUserId(user.getId());
			if(resData != null) {
				model.addAttribute("res", resData);
			} 
			return RES_INSERT_FORM; 
		}

		Restaurant res = new Restaurant();
		res.setResName(resForm.getResName());
		res.setResAddress(resForm.getResAddress());
		res.setResPhone(resForm.getResPhone());
		res.setOwnerId(userId);
		res.setResDescription(resForm.getResDescription());
		res.setCategoryId(resForm.getCategoryId());
		res.setTogetherOk(resForm.isTogetherOk());
		res.setArea(resForm.getResArea());
		res.setImgFile(FoodFunction.getImgFile(resForm));

		if(request.getParameter("status").equals("insert")) {
			res.setRevCount(0);
			res.setAvgRating(FoodFunction.defaultDouble);
			alonealong.insertRestaurant(res);
		}
		else if(request.getParameter("status").equals("update")) {
			long resId = Long.parseLong(request.getParameter("resId"));
			Restaurant orgin = alonealong.getRestaurantByResId(resId);
			res.setResId(resId);
			res.setAvgRating(orgin.getAvgRating());
			res.setRevCount(orgin.getRevCount());
			res.setResDate(orgin.getResDate());
			if(res.getImgFile().length == 0){
				res.setImgFile(orgin.getImgFile());
			}
			alonealong.updateRestaurant(res);	
		}
		
//		List<Restaurant> restaurantList = alonealong.getRestaurantList();
//		model.addAttribute("restaurantList", restaurantList);

		return "redirect:/eating";

	}
	
}
