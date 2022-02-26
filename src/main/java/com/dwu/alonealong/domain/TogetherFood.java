package com.dwu.alonealong.domain;

import java.io.Serializable;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="TOGETHER_FOOD")
@SequenceGenerator(
		name = "TOGFOOD_SEQ_GENERATOR"
		, sequenceName = "TOGFOOD_ID_SEQ"
		, initialValue = 1
		, allocationSize = 1
)
public class TogetherFood implements Serializable {
	@Id
	@Column(name = "togfood_id")
	private String togetherFoodId;
	@Column(name = "tog_id")
	private String togetherId;
	@Column(name = "food_id")
	private long foodId;
	private int quantity;
	
	@ManyToOne
	@JoinColumn(name="food_id", insertable=false, updatable=false)
	private Food food;
	
	public TogetherFood(String togetherFoodId, String togetherId, long foodId, int quantity) {
		super();
		this.togetherFoodId = togetherFoodId;
		this.togetherId = togetherId;
		this.foodId = foodId;
		this.quantity = quantity;
	}

	public TogetherFood() { }

	public String getTogetherFoodId() {return togetherFoodId;}
	public void setTogetherFoodId(String togetherFoodId) {this.togetherFoodId = togetherFoodId;}
	
	public String getTogetherId() {return togetherId;}
	public void setTogetherId(String togetherId) {this.togetherId = togetherId;}
	
	public long getFoodId() {return foodId;}
	public void setFoodId(long foodId) {this.foodId = foodId;}
	
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
