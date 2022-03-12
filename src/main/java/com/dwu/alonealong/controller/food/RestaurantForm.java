package com.dwu.alonealong.controller.food;

import java.net.URL;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;


@Getter
@Setter
public class RestaurantForm {
	
	private String resName;
	private String categoryId; //음식분류
	private String resAddress;
	private String resPhone;
	private String resDescription;
	private String resArea;
	private MultipartFile imgFile;
	private boolean togetherOk;
	private boolean open;
	private String openTime;
	private String closeTime;
	
	private String img64;

}
