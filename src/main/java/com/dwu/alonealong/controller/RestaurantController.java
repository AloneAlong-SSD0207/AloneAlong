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

import com.dwu.alonealong.domain.Restaurant;
import com.dwu.alonealong.domain.User;
import com.dwu.alonealong.service.AloneAlongFacade;
import com.dwu.alonealong.service.RestaurantFormValidator;

@Controller
@RequestMapping("/eating/adminRes")
public class RestaurantController {
	
	private static final String RES_INSERT_FORM = "restaurantForm";
	private AloneAlongFacade alonealong;
	public static final Double defaultDouble = 0.0;
	
	@Autowired
	public void setAlonealong(AloneAlongFacade alonealong) {
		this.alonealong = alonealong;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public String restaurantForm(
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
	public String insertAndUpdateRestaurant(
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
		
		Restaurant res;
		
		if(request.getParameter("status").equals("insert")) {
			res = new Restaurant("RES_ID.NEXTVAL", resForm.getResName(), resForm.getResAddress(), resForm.getResPhone(), userId,
					resForm.getResDescription(), defaultDouble, resForm.getCategoryId(), getImgFile(resForm), resForm.getIsTogetherOk(), resForm.getResArea());
			alonealong.insertRestaurant(res);
		}
		else if(request.getParameter("status").equals("update")) {
			String resId = request.getParameter("resId");
			res = new Restaurant(resId, resForm.getResName(), resForm.getResAddress(), resForm.getResPhone(),
					resForm.getResDescription(), resForm.getCategoryId(), getImgFile(resForm), resForm.getIsTogetherOk(), resForm.getResArea());
			alonealong.updateRestaurant(res);	
		}
		
		List<Restaurant> restaurantList = alonealong.getRestaurantList();
		model.addAttribute("restaurantList", restaurantList);

		return "redirect:/eating";

	}
	private byte[] getImgFile(RestaurantForm resForm) {
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
