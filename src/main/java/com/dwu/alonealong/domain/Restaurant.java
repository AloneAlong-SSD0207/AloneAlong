package com.dwu.alonealong.domain;



import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Arrays;

import javax.persistence.*;

import lombok.*;

@SuppressWarnings("serial")
@Entity
@Getter
@Setter
@SequenceGenerator(
		name = "RES_SEQ_GENERATOR"
		, sequenceName = "RESID_SEQ"
		, initialValue = 1
		, allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Restaurant implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RES_SEQ_GENERATOR")
	@Column(name="res_id")
	private long resId;
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

	@Column(name="together_ok")
	private boolean togetherOk;
	@Column(name="area")
	private String area;
	@Column(name="res_date")
	private LocalDateTime resDate = LocalDateTime.now();
	@Column(name="rev_count")
	private int revCount;

	@Transient
	private String img64;
	@Transient
	private User owner;
	@Transient
	private FoodReview[] reviewList;
	@Transient
	private Food[] menuList;
	@Transient
	private Together[] togetherList;

	@Override
	public String toString() {
		return "Restaurant [resId=" + resId + ", resName=" + resName + ", resAddress=" + resAddress + ", resPhone="
				+ resPhone + ", ownerId=" + ownerId + ", resDescription=" + resDescription + ", avgRating=" + avgRating
				+ ", categoryId=" + categoryId + ", imgFile=" + Arrays.toString(imgFile) + ", img64=" + img64
				+ ", isTogetherOk=" + togetherOk + ", area=" + area + ", res_date=" + resDate + ", owner=" + owner
				+ ", reviewList=" + Arrays.toString(reviewList) + ", menuList=" + Arrays.toString(menuList)
				+ ", togetherList=" + Arrays.toString(togetherList) + "]";
	}
	
	
}