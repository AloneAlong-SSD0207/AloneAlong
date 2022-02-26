package com.dwu.alonealong.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="food_order")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class FoodOrder implements Serializable{
	
	@Id
	@Column(name="order_id")
	String orderId;
	@Column(name="reserve_type")
	String reserveType;
	@Column(name="visit_date")
	String visitDate;
	@Column(name="food_id")
	long foodId;
	@Column(name="res_id")
	long resId;

	@Transient
	String userId;
	@Transient
	List<FoodCartItem> foodList;
	@Transient
	List<FoodLineItem> orderedList;
	@Transient
	Payment payment;
	@Transient
	String firstFoodName;
	@Transient
	byte[] resImg;
	@Transient
	String resName;
	@Transient
	String img64;
	@Transient
	int totalPrice;
	@Transient
	String orderDate;

  //togetherOrder 넣기 위해 추가

	
	//주문용 생성자
	public FoodOrder(long resId, String userId, List<FoodCartItem> foodList, String reserveType, String visitDate, Payment payment) {
		super();
		this.resId = resId;
		this.userId = userId;
		this.foodList = foodList;
		this.reserveType = reserveType;
		this.visitDate = visitDate;
		this.payment = payment;
	}
	//주문확인용 생성자
	public FoodOrder(String orderId, String orderDate, int totalPrice, long resId, List<FoodLineItem> orderedList, String reserveType, String visitDate, String cardName) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.resId = resId;
		this.orderedList = orderedList;
		this.reserveType = reserveType;
		this.visitDate = visitDate;
		this.payment.setCard_name(cardName);
	}
	//Together order시에 foodOrder를 위한 생성자
	public FoodOrder(String orderId, String reserveType, String visitDate, long foodId, long resId) {
		super();
		this.resId = resId;
		this.reserveType = reserveType;
		this.visitDate = visitDate;
		this.orderId = orderId;
		this.foodId = foodId;
	}

	public int calcTotalPrice() {
		int total = 0;
		for(FoodCartItem item : foodList) {
			total += item.getUnitPrice();
		}
		return total;
	}

	@Override
	public String toString() {
		return "FoodOrder [resId=" + resId + ", userId=" + userId + ", foodList=" + foodList + ", reserveType="
				+ reserveType + ", visitDate=" + visitDate + ", payment=" + payment + "]";
	}
}
