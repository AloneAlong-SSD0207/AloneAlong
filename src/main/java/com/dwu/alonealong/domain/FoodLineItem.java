package com.dwu.alonealong.domain;

import java.io.Serializable;

import javax.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Table(name="food_lineitem")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@IdClass(FoodIdClass.class)
public class FoodLineItem implements Serializable{

	@Id
	@Column(name="order_id")
	private String orderId;
	@Id
	@Column(name="food_id")
	private long foodId;
	@Column(name="quantity")
	private int quantity;
	@Column(name="unit_price")
	private int unitPrice;
	
	@Transient
	private String foodName;

	//조회용 생성자
	public FoodLineItem(String foodName, int quantity, int unitPrice) {
		super();
		this.setFoodName(foodName);
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}
	@Builder
	public FoodLineItem(String orderId, long foodId, int quantity, int unitPrice) {
		this.orderId = orderId;
		this.foodId = foodId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	@Override
	public String toString() {
		return "FoodLineItem [orderId=" + orderId + ", foodId=" + foodId + ", quantity=" + quantity + ", unitPrice="
				+ unitPrice + "]";
	}

}
