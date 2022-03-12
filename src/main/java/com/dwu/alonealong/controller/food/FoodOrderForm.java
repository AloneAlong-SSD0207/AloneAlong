package com.dwu.alonealong.controller.food;

import java.util.Date;

import com.dwu.alonealong.domain.FoodCart;
import com.dwu.alonealong.domain.Payment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FoodOrderForm {
	
	private FoodCart foodCart;
	private String reserveType;
	private String visitDate;
	private String visitTime;
	String ccName;
	String ccNum;
	String ccDate;
	String ccCVC;
	private Payment payment;
	
	FoodOrderForm(){
		
	}

	public FoodOrderForm(FoodCart foodCart, String reserveType, String visitDate, Payment payment) {
		super();
		this.foodCart = foodCart;
		this.reserveType = reserveType;
		this.visitDate = visitDate;
		this.payment = payment;
	}

	
}
