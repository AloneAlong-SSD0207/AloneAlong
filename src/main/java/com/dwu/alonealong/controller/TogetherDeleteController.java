package com.dwu.alonealong.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.dwu.alonealong.service.AloneAlongFacade;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TogetherDeleteController {
	private AloneAlongFacade aloneAlong;
	
	@Autowired
	public void setAlonealong(AloneAlongFacade aloneAlong) {
		this.aloneAlong = aloneAlong;
	}

	@DeleteMapping("/together/delete/{togetherId}")
	public void deleteTogether(@PathVariable("togetherId") String togId) {
		String orderId = aloneAlong.getTogetherOrderByTogId(togId).get(0).getOrderId();

		aloneAlong.deleteTogetherFood(togId); //음식 리스트 삭제
		aloneAlong.deleteTogetherMember(togId); //멤버 삭제
		aloneAlong.deleteTogetherOrder(togId); //TogetherOrder 삭제
		aloneAlong.deleteTogetherOrderInfo(orderId); //OrderInfo 삭제
		aloneAlong.deleteTogether(togId); //together 삭제
	}
	
}
