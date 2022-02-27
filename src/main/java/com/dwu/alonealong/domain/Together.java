package com.dwu.alonealong.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@Table(name="TOGETHER")
@Getter
@Setter
public class Together implements Serializable {
	public static final int MIN_HEAD_COUNT = 2;
	public static final int GATHERING = 0;
	public static final int GATHERED = 1;
	
	@Id
	@Column(name="tog_id")
	private String togetherId;
	@Column(name="tog_name")
	private String togetherName;
	@Column(name="headcount")
	private int headCount;
	@Column(name="tog_date")
	private String togetherDate;
	@Column(name="tog_time")
	private String togetherTime;
	@Column(name="tog_sex")
	private String sex;
	@Column(name="tog_age")
	private String age;
	@Column(name="tog_des")
	private String togetherDes;
	@Column(name="res_id")
	private long resId;
	@Column(name="tog_status")
	private int status;
	private int price;
	
	@ManyToOne
	@JoinColumn(name = "res_id", insertable=false, updatable=false)
	private Restaurant restaurant;

	@Transient
	private List<TogetherFood> togetherFoodList = new ArrayList<TogetherFood>();

	@Transient
	private List<TogetherMember> togetherMemberList = new ArrayList<TogetherMember>();
	
	public Together() {
		this.headCount = MIN_HEAD_COUNT;
	}
	
	public Together(String togetherId, String togetherName, int headCount, String togetherDate, String togetherTime,
			String sex, String age, String togetherDes, long resId, int status, int price) {
		super();
		this.togetherId = togetherId;
		this.togetherName = togetherName;
		this.headCount = headCount;
		this.togetherDate = togetherDate;
		this.togetherTime = togetherTime;
		this.sex = sex;
		this.age = age;
		this.togetherDes = togetherDes;
		this.resId = resId;
		this.status = status;
		this.price = price;
	}

	//음식 총합 구하기
	public int getTotalPrice() {
		int sum = 0;
		for(int i = 0; i < togetherFoodList.size(); i++) {
			sum += togetherFoodList.get(i).getFood().getPrice() * togetherFoodList.get(i).getQuantity();
		}
		return sum;
	}

	//1인당 음식 가격 구하기
	public int getPricePerPerson() {
		return getTotalPrice() / headCount;
	}

	//주소에서 앞에 두글자 따서 태그 구하기
	public String getAddressTag() {
		String address = restaurant.getResAddress();
		StringTokenizer st = new StringTokenizer(address);
		String addressTag = st.nextToken();

		return addressTag;
	}
	
}
