package com.dwu.alonealong.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name="TOGETHER")
public class Together implements Serializable {
	public static final int MIN_HEAD_COUNT = 2;
	public static final int GATHERING = 0;
	public static final int GATHERED = 1;
	
	@Id
	@Column(name="tog_id")
	private String togetherId;
	@Column(name="tog_name")
	private String togetherName;
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
	
	@OneToMany
	@JoinColumn(name="tog_id")
	private List<TogetherFood> togetherFoodList = new ArrayList<TogetherFood>();
	
	@OneToMany(fetch = FetchType.EAGER)
	@JoinColumn(name="tog_id")
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

	public Together(String name, int headCount, int headCount1, long resId, String date, String time, String sex, String age, int gathering, long resId1, int price) {
	//오류나서 만든 임의 생성자. 삭제요망
	}

	public Together(long resId, String s, int headCount, String name, int i, String sex, String age, int gathering, String description, String date, String time) {
	//오류나서 만든 임의 생성자. 삭제요망
	}

	public String getTogetherId() {return togetherId;}
	public void setTogetherId(String togetherId) {this.togetherId = togetherId;}
	
	public String getTogetherName() {return togetherName;}
	public void setTogetherName(String togetherName) {this.togetherName = togetherName;}
	
	public int getHeadCount() {return headCount;}
	public void setHeadCount(int headCount) {this.headCount = headCount;}
	
	public String getTogetherDate() {return togetherDate;}
	public void setTogetherDate(String togetherDate) {this.togetherDate = togetherDate;}
	
	public String getTogetherTime() {return togetherTime;}
	public void setTogetherTime(String togetherTime) {this.togetherTime = togetherTime;}

	public String getSex() {return sex;}
	public void setSex(String sex) {this.sex = sex;}
	
	public String getAge() {return age;}
	public void setAge(String age) {this.age = age;}
	
	public String getTogetherDes() {return togetherDes;}
	public void setTogetherDes(String togetherDes) {this.togetherDes = togetherDes;}

	public long getResId() {return resId;}
	public void setResId(long resId) {this.resId = resId;}

	public int getStatus() {return status;}
	public void setStatus(int status) {this.status = status;}

	public int getPrice() {return price;}
	public void setPrice(int price) {this.price = price;}

	public Restaurant getRestaurant() {return restaurant;}
	public void setRestaurant(Restaurant restaurant) {this.restaurant = restaurant;}

	public List<TogetherFood> getTogetherFoodList() {return togetherFoodList;}
	public void setTogetherFoodList(List<TogetherFood> togetherFoodList) {this.togetherFoodList = togetherFoodList;}

	public List<TogetherMember> getTogetherMemberList() {return togetherMemberList;}
	public void setTogetherMemberList(List<TogetherMember> togetherMemberList) {this.togetherMemberList = togetherMemberList;}

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
