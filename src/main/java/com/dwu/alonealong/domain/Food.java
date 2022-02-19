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
public class Food implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="food_id")
	private String foodId;
	@Column(name="res_id")
	private String resId;
	//private String ownerId; //일단 필요없음.
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
	
	public Food() {
	}
	
	public Food(String resId, String id, String name, int price, String des, byte[] img, int maxPeopleNum){
		this.resId = resId;
		this.foodId = id;
		this.name = name;
		this.price = price;
		this.description = des;
		this.imgFile = img;
		this.maxPeopleNum = maxPeopleNum;
	}
	

	@Override
	public String toString() {
		return "Food [foodId=" + foodId + ", resId=" + resId + ", ownerId=" + ", price=" + price + ", name="
				+ name + ", description=" + description + ", maxPeopleNum=" + maxPeopleNum + "]";
	}
	public String getFoodId() {
		return foodId;
	}
	public void setFoodId(String foodId) {
		this.foodId = foodId;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
//	public String getOwnerId() {
//		return ownerId;
//	}
//	public void setOwnerId(String ownerId) {
//		this.ownerId = ownerId;
//	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getMaxPeopleNum() {
		return maxPeopleNum;
	}
	public void setMaxPeopleNum(int maxPeopleNum) {
		this.maxPeopleNum = maxPeopleNum;
	}

	public Food(String foodId, String resId, int price, String name, String description, byte[] img,
			int maxPeopleNum) {
		super();
		this.foodId = foodId;
		this.resId = resId;
		this.price = price;
		this.name = name;
		this.description = description;
		this.imgFile = img;
		this.maxPeopleNum = maxPeopleNum;
	}

	public byte[] getImgFile() {
		return imgFile;
	}

	public void setImgFile(byte[] imgFile) {
		this.imgFile = imgFile;
	}

	public String getImg64() {
		return img64;
	}

	public void setImg64(String img64) {
		this.img64 = img64;
	}
	
	
	
}
