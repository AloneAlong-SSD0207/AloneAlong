package com.dwu.alonealong.controller;

import java.io.File;
import java.net.URL;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FoodForm {
	private Long foodId;
	private long resId;
	private int price;
	private String name;
	private String description;
	private MultipartFile imgFile;
	private int maxPeopleNum;
	
	private String img64;
	
	FoodForm(){
		
	}

}
