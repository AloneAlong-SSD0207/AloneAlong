package com.dwu.alonealong.controller;

import java.io.File;
import java.net.URL;

public class FoodForm {
	private String foodId;
	private String resId;
	private String ownerId; //일단 필요없음
	private int price;
	private String name;
	private String description;
	private File imgFile;
	private URL img;
	private int maxPeopleNum;
	
	FoodForm(){
		
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
	public String getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}
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



	public File getImgFile() {
		return imgFile;
	}



	public void setImgFile(File imgFile) {
		this.imgFile = imgFile;
	}
	
	
}
