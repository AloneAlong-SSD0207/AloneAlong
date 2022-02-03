package com.dwu.alonealong.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.dwu.alonealong.domain.FoodOrder;
import com.dwu.alonealong.domain.Order;
import com.dwu.alonealong.domain.Payment;
import com.dwu.alonealong.domain.Together;
import com.dwu.alonealong.domain.TogetherMember;
import com.dwu.alonealong.domain.TogetherOrder;
import com.dwu.alonealong.domain.User;
import com.dwu.alonealong.service.AloneAlongFacade;

@Controller
@SessionAttributes({"together"})
public class TogetherOrderController {
	public static Order order;

	private AloneAlongFacade aloneAlong;
	
	@Autowired
	public void setAlonealong(AloneAlongFacade aloneAlong) {
		this.aloneAlong = aloneAlong;
	}
	
	//신청하기	
	@RequestMapping(value = "/togetherOrder")
	public String joinTogether(HttpServletRequest request,
			@ModelAttribute("together") Together together) {
		
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		
		if(userSession == null) {
			return "redirect:/login";
		}
		
		return "together/togetherOrder";
//		return "togetherOrder";
	}
	
	//결제하기
	@RequestMapping("/togetherOrder/complete")
//	@PostMapping(value = "/togetherOrder/complete")
	public String orderTogether(
			HttpServletRequest request,
			@ModelAttribute("together") Together together,
			@RequestParam("cardName") String cardName,
			@RequestParam("cardNum") String cardNum,
			@RequestParam("cardDate1") String cardDate1,
			@RequestParam("cardDate2") String cardDate2,
			ModelMap model) {
		
		UserSession userSession = (UserSession)request.getSession().getAttribute("userSession");
		User user = aloneAlong.getUserByUserId(userSession.getUser().getId());
		
		String orderDate = cardDate1 + "/" + cardDate2;
		
		if(isHost(together, user)) {
			completePayment(true, together, user, cardNum, orderDate, cardName); //카드 결제
			
		} else {
			completePayment(false, together, user, cardNum, orderDate, cardName); //카드 결제
			insertUserIntoMember(user, together); //같이먹기 멤버에 추가
			
			if(isMemberFull(together)) {
				completeGathering(together); //모집 완료로 바꿈
				insertFoodOrder(together); //식당 측 foodOrder에 넣기
			}
		}
		
		String togName = aloneAlong.getTogetherByTogId(together.getTogetherId()).getTogetherName();
		model.put("togName", togName);
		
		return "togOrderResult";
	}

	public boolean isHost(Together together, User user) {
		if(together.getTogetherMemberList().get(0).getUserId().equals(user.getId())) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean isMemberFull(Together together) {
		if(together.getTogetherMemberList().size() + 1 == together.getHeadCount()) {
			return true;
		} else {
			return false;
		}
	}
	
	public void completePayment(boolean isHost, Together together, User user, String cardNum, String orderDate, String cardName) {
		if(isHost) {
			String orderId = aloneAlong.getTogetherOrderByTogId(together.getTogetherId()).get(0).getOrderId();
			order = new Order(orderId, together.getPrice(), "결제완료", user.getId(), cardNum, orderDate, cardName);
			aloneAlong.updateTogetherOrderInfo(order);
		}else {
			order = new Order("ORDER_ID.NEXTVAL", together.getPrice(), "결제완료", user.getId(), cardNum, orderDate, cardName);
			aloneAlong.insertTogetherOrderInfo(order);
			
			//주문목록에 넣기
			TogetherOrder togetherOrder = new TogetherOrder(order.getOrderId(), together.getTogetherId());
			aloneAlong.insertTogetherOrder(togetherOrder);
		}
	}
	
	public void insertUserIntoMember(User user, Together together) {
		TogetherMember togetherMember = new TogetherMember("TOGMEM_ID.NEXTVAL", user.getId(), together.getTogetherId(), TogetherMember.IS_NOT_HOST);
		aloneAlong.insertTogetherMember(togetherMember);
	}
	
	public void completeGathering(Together together) {
		Together newTogether = new Together(together.getTogetherId(), together.getTogetherName(), together.getHeadCount(), together.getTogetherDate(), together.getTogetherTime(), 
				together.getSex(), together.getAge(), together.getTogetherDes(), together.getResId(), Together.GATHERED, together.getPrice());
		aloneAlong.updateTogether(newTogether);
	}
	
	public void insertFoodOrder(Together together) {
		for(int i = 0; i < together.getTogetherFoodList().size(); i++) {
			FoodOrder foodOrder = new FoodOrder(order.getOrderId(), "visit", together.getTogetherDate() + "," + together.getTogetherTime(), 
					together.getTogetherFoodList().get(i).getFoodId(), together.getResId());
			aloneAlong.insertFoodOrderForTogetherOrder(foodOrder);
		}
	}
}
