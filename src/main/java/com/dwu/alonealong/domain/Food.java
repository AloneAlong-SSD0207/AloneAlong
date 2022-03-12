package com.dwu.alonealong.domain;

import java.io.File;
import java.io.Serializable;
import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.*;

import lombok.*;

@Entity
@Getter
@Setter
@SequenceGenerator(
		name = "FOOD_SEQ_GENERATOR"
		, sequenceName = "FOODID_SEQ"
		, initialValue = 1
		, allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Food implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FOOD_SEQ_GENERATOR")
	@Column(name="food_id")
	private long foodId;
	@Column(name="res_id")
	private long resId;
	@Column(name="food_price")
	private int price;
	@Column(name="food_name")
	private String name;
	@Column(name="food_des")
	private String description;
	@Column(name="food_img")
	private byte[] imgFile;
	@Transient
	private String img64;
	@Column(name="max_people_num")
	private int maxPeopleNum;
	@Column(name="food_sell")
	private String foodSell;

	public String getImg64() {
		return img64;
	}

	public void setImg64(String img64) {
		this.img64 = img64;
	}

	@Override
	public String toString() {
		return "Food [foodId=" + foodId + ", resId=" + resId + ", ownerId=" + ", price=" + price + ", name="
				+ name + ", description=" + description + ", maxPeopleNum=" + maxPeopleNum + "]";
	}

}