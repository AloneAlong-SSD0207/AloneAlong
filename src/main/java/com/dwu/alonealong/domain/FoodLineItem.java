package com.dwu.alonealong.domain;

import java.io.Serializable;

import javax.persistence.*;
import lombok.*;

@Getter
@Setter
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class FoodLineItem implements Serializable{
	
	@Id
	@Column(name="order_id")
	private String orderId;
	@Column(name="food_id")
	private String foodId;
	@Column(name="quantity")
	private int quantity;
	@Column(name="unit_price")
	private int unitPrice;
	
	@Transient
	private String foodName;
	
	public FoodLineItem() {
		
	}
	
	public FoodLineItem(String orderId, String foodId, int quantity, int unitPrice) {
		super();
		this.orderId = orderId;
		this.foodId = foodId;
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}
	//조회용 생성자
	public FoodLineItem(String foodName, int quantity, int unitPrice) {
		super();
		this.setFoodName(foodName);
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}
	
	@Override
	public String toString() {
		return "FoodLineItem [orderId=" + orderId + ", foodId=" + foodId + ", quantity=" + quantity + ", unitPrice="
				+ unitPrice + "]";
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getFoodId() {
		return foodId;
	}
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public int getUnitPrice() {
		return unitPrice;
	}
	public void setUnitPrice(int unitPrice) {
		this.unitPrice = unitPrice;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	
	
	
}
