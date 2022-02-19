package com.dwu.alonealong.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="TOGETHER_FOOD")
public class TogetherFood implements Serializable {
	@Id
	@Column(name = "togFood_id")
	private String togetherFoodId;
	@Column(name = "tog_id")
	private String togetherId;
	@Column(name = "food_id")
	private String foodId;
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name="food_id", insertable=false, updatable=false)
	private Food food;
	
	public TogetherFood(String togetherFoodId, String togetherId, String foodId, int quantity) {
		super();
		this.togetherFoodId = togetherFoodId;
		this.togetherId = togetherId;
		this.foodId = foodId;
		this.quantity = quantity;
	}
	
	public String getTogetherFoodId() {return togetherFoodId;}
	public void setTogetherFoodId(String togetherFoodId) {this.togetherFoodId = togetherFoodId;}
	
	public String getTogetherId() {return togetherId;}
	public void setTogetherId(String togetherId) {this.togetherId = togetherId;}
	
	public String getFoodId() {return foodId;}
	public void setFoodId(String foodId) {this.foodId = foodId;}
	
	public int getQuantity() {return quantity;}
	public void setQuantity(int quantity) {this.quantity = quantity;}
	
	public Food getFood() {return food;}
	public void setFood(Food food) {this.food = food;}
	
	@Override
	public String toString() {
		return "TogetherFood [togetherFoodId=" + togetherFoodId + ", togetherId=" + togetherId + ", foodId=" + foodId
				+ ", quantity=" + quantity + ", food=" + food + "]";
	}
	
	//음식가격 * 수량 구하기
	public int getUnitTotalPrice() {
		return food.getPrice() * quantity;
	}
	
}
