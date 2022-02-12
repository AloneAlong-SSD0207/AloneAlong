package com.dwu.alonealong.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dwu.alonealong.domain.FoodCart;
import com.dwu.alonealong.domain.Together;
import com.dwu.alonealong.domain.User;
import com.dwu.alonealong.service.AloneAlongFacade;

@Controller
@SessionAttributes({"userSession", "sessionFoodCart", "together"})
public class ViewTogetherListController {
	public static String areaName, kindName, priceName, sexName, ageName;
	public static final int PAGE_SIZE = 10;
	public static final int PAGE_RANGE = 5;

	private AloneAlongFacade alonealong;
	
	@Autowired
	public void setAlonealong(AloneAlongFacade alonealong) {
		this.alonealong = alonealong;
	}
	
	//카테고리별 리스트
	@GetMapping("/together")
	public String listTogetherByCategory(
			HttpServletRequest request,
			@RequestParam(value="page", defaultValue="1") int page, 
			@RequestParam(value="area",  defaultValue="all") String area,
			@RequestParam(value="date",  defaultValue="") String date,
			@RequestParam(value="kind",  defaultValue="all") String kind,
			@RequestParam(value="price",  defaultValue="99999") int price,
			@RequestParam(value="sex",  defaultValue="all") String sex,
			@RequestParam(value="age",  defaultValue="all") String age,
			ModelMap model) throws Exception {
		model.addAttribute("sessionFoodCart", new FoodCart()); //카트 초기화
		model.addAttribute("together", new Together()); //together 초기화
		
		//유저 맞춤 추천기능
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		
		if(userSession != null) {
			User user = alonealong.getUserByUserId(userSession.getUser().getId());
			
			List<Together> recommandList = this.alonealong.recommandTogetherList(user.getSex(), user.getAddress());
			
			model.put("userSession", userSession);
			model.put("user", user);
			model.put("recommandList", recommandList);
		}
		
		//공통 리스트
		List<Together> togetherList = this.alonealong.getTogetherListByCategory(area, date, kind, price, sex, age);
		PagedListHolder<Together> togetherPagedList = new PagedListHolder<Together>(togetherList);
		togetherPagedList.setPageSize(PAGE_SIZE);
		togetherPagedList.setPage(page - 1);
		
		model.put("togetherList", togetherPagedList.getPageList());
		model.put("togetherCount", togetherList.size());
		
		model.put("page", togetherPagedList.getPage() + 1);
		model.put("startPage", (togetherPagedList.getPage() / PAGE_RANGE) * PAGE_RANGE + 1);
		model.put("lastPage", togetherPagedList.getPageCount());
		
		//카테고리
		getCategory(area, kind, price, sex, age);
		
		model.put("areaName", areaName);
		model.put("kindName", kindName);
		model.put("priceName", priceName);
		model.put("sexName", sexName);
		model.put("ageName", ageName);
		
		model.put("area", area);
		model.put("date", date);
		model.put("kind", kind);
		model.put("price", price);
		model.put("sex", sex);
		model.put("age", age);
		
		return "togetherList";
	}
	
	public void getCategory(String area, String kind, int price, String sex, String age) {
		areaName = "모든 지역";
		switch(area) {
			case "seoul" : areaName = "서울특별시"; break;
			case "gyenggi" : areaName = "경기도"; break;
			case "busan" : areaName = "부산광역시"; break;
			case "incheon" : areaName = "인천광역시"; break;
			case "deagu" : areaName = "대구광역시"; break;
			case "deageon" : areaName = "대전광역시"; break;
			case "guangju" : areaName = "광주광역시"; break;
			case "ulsan" : areaName = "울산광역시"; break;
		}
		
		kindName = "모든 종류 음식";
		switch(kind) {
			case "korean" : kindName = "한식"; break;
			case "western" : kindName = "양식"; break;
			case "japanese" : kindName = "일식"; break;
			case "chinese" : kindName = "중식"; break;
			case "etc" : kindName = "기타"; break;
		}
		
		priceName = "모든 가격대";
		switch(price) {
			case 10000 : priceName = "10000원 미만"; break;
			case 15000 : priceName = "15000원 미만"; break;
			case 20000 : priceName = "20000원 미만"; break;
			case 25000 : priceName = "25000원 미만"; break;
			case 35000 : priceName = "30000원 미만"; break;
		}
		
		sexName = "모든 성별";
		switch(sex) {
			case "female" : sexName = "여성"; break;
			case "male" : sexName = "남성"; break;
		}
		
		ageName = "모든 나이";
		switch(age) {
			case "10" : ageName = "10대"; break;
			case "20" : ageName = "20대"; break;
			case "30" : ageName = "30대"; break;
			case "40" : ageName = "40대"; break;
			case "50" : ageName = "50대 이상"; break;
		}
	}
	
}
