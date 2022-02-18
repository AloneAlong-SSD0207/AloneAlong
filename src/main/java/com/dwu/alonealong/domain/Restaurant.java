package com.dwu.alonealong.domain;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.*;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Restaurant implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="res_id")
	private String resId;
	@Column(name="res_name")
	private String resName;
	@Column(name="res_address")
	private String resAddress;
	@Column(name="res_phone")
	private String resPhone;
	@Column(name="owner_id")
	private String ownerId;
	@Column(name="res_des")
	private String resDescription;
	@Column(name="avg_rating")
	private Double avgRating;
	@Column(name="res_category")
	private String categoryId; //음식분류
	@Column(name="res_img")
	private byte[] imgFile;
	@Transient
	private String img64;
	@Column(name="is_together_ok")
	private boolean isTogetherOk;
	@Column(name="area")
	private String area;
	@Column(name="res_date")
	private LocalDateTime resDate = LocalDateTime.now();
	@Column(name="rev_count")
	private int revCount;
	
	
	@Transient
	private User owner;
	@Transient
	private FoodReview[] reviewList;
	@Transient
	private Food[] menuList;
	@Transient
	private Together[] togetherList;
	
	//조회용
	@Builder
	public Restaurant(String resId, String resName, String resAddress, String resPhone, String ownerId,
			String resDescription, Double avgRating, String categoryId, byte[] imgFile, boolean isTogetherOk, String area) {
		super();
		this.resId = resId;
		this.resName = resName;
		this.resAddress = resAddress;
		this.resPhone = resPhone;
		this.ownerId = ownerId;
		this.resDescription = resDescription;
		this.avgRating = avgRating;
		this.categoryId = categoryId;
		this.imgFile = imgFile;
		this.isTogetherOk = isTogetherOk;
		this.area = area;
	}
	
	//생성용
	@Builder
	public Restaurant(String resId, String resName, String resAddress, String resPhone,
			String resDescription, String categoryId, byte[] imgFile, boolean isTogetherOk, String area) {
		super();
		this.resId = resId;
		this.resName = resName;
		this.resAddress = resAddress;
		this.resPhone = resPhone;
		this.resDescription = resDescription;
		this.categoryId = categoryId;
		this.imgFile = imgFile;
		this.isTogetherOk = isTogetherOk;
		this.area = area;
	}
	
	@Builder
	public Restaurant(){
		
	}
	public LocalDateTime getResDate() {
		return resDate;
	}

	public void setResDate(LocalDateTime resDate) {
		this.resDate = resDate;
	}

	public int getRevCount() {
		return revCount;
	}

	public void setRevCount(int revCount) {
		this.revCount = revCount;
	}


	
	public String getArea() {
		return area;
	}


	public void setArea(String area) {
		this.area = area;
	}


	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getResName() {
		return resName;
	}
	public void setResName(String resName) {
		this.resName = resName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getResAddress() {
		return resAddress;
	}
	public void setResAddress(String resAddress) {
		this.resAddress = resAddress;
	}
	public User getOwner() {
		return owner;
	}
	public void setOwner(User owner) {
		this.owner = owner;
	}
	public String getResPhone() {
		return resPhone;
	}
	public void setResPhone(String resPhone) {
		this.resPhone = resPhone;
	}
	public String getResDescription() {
		return resDescription;
	}
	public void setResDescription(String resDescription) {
		this.resDescription = resDescription;
	}
	public Double getAvgRating() {
		return avgRating;
	}
	public void setAvgRating(Double avgRating) {
		this.avgRating = avgRating;
	}

	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
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
	public boolean getIsTogetherOk() {
		return isTogetherOk;
	}
	public boolean isTogetherOk() {
		return isTogetherOk;
	}
	public void setTogetherOk(boolean isTogetherOk) {
		this.isTogetherOk = isTogetherOk;
	}
	public FoodReview[] getReviewList() {
		return reviewList;
	}
	public void setReviewList(FoodReview[] reviewList) {
		this.reviewList = reviewList;
	}
	public Food[] getMenuList() {
		return menuList;
	}
	public void setMenuList(Food[] menuList) {
		this.menuList = menuList;
	}
	public Together[] getTogetherList() {
		return togetherList;
	}
	public void setTogetherList(Together[] togetherList) {
		this.togetherList = togetherList;
	}

	@Override
	public String toString() {
		return "Restaurant [resId=" + resId + ", resName=" + resName + ", resAddress=" + resAddress + ", resPhone="
				+ resPhone + ", ownerId=" + ownerId + ", resDescription=" + resDescription + ", avgRating=" + avgRating
				+ ", categoryId=" + categoryId + ", imgFile=" + Arrays.toString(imgFile) + ", img64=" + img64
				+ ", isTogetherOk=" + isTogetherOk + ", area=" + area + ", res_date=" + resDate + ", owner=" + owner
				+ ", reviewList=" + Arrays.toString(reviewList) + ", menuList=" + Arrays.toString(menuList)
				+ ", togetherList=" + Arrays.toString(togetherList) + "]";
	}
	
	
}