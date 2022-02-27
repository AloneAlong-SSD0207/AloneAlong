package com.dwu.alonealong.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

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
@NoArgsConstructor
@Getter
@Setter
@ToString
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

	//음식가격 * 수량 구하기
	public int getUnitTotalPrice() {
		return food.getPrice() * quantity;
	}
	
}
