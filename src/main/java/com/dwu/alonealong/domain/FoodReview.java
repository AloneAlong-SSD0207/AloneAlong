package com.dwu.alonealong.domain;

import java.io.Serializable;
import javax.persistence.*;
import lombok.*;

@SuppressWarnings("serial")
@Entity
public class FoodReview implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@Column(name="review_id")
	String reviewId;
	@Column(name="order_id")
	String orderId;
	@Column(name="res_id")
	String resId;
	@Column(name="user_id")
	String userId;
	@Column(name="review_date")
	String reviewDate;
	@Column(name="review_rating")
	int rating;
	@Column(name="review_contents")
	String contents;
	@Column(name="review_recommend")
	int recommend;
	
	@Transient
	String userNickName;
	
	
	public String getUserNickName() {
		return userNickName;
	}
	public void setUserNickName(String userNickName) {
		this.userNickName = userNickName;
	}
	public FoodReview() {
		
	}
	public FoodReview(String orderId, String resId, String userId, int rating,
			String contents, int recommend) {
		super();
		this.orderId = orderId;
		this.resId = resId;
		this.userId = userId;
		this.rating = rating;
		this.contents = contents;
		this.recommend = recommend;
	}
	public FoodReview(String reviewId, String orderId, String resId, String userId, String reviewDate, int rating,
			String contents, int recommend) {
		super();
		this.reviewId = reviewId;
		this.orderId = orderId;
		this.resId = resId;
		this.userId = userId;
		this.reviewDate = reviewDate;
		this.rating = rating;
		this.contents = contents;
		this.recommend = recommend;
	}
	public String getReviewId() {
		return reviewId;
	}
	public void setReviewId(String reviewId) {
		this.reviewId = reviewId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getResId() {
		return resId;
	}
	public void setResId(String resId) {
		this.resId = resId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
	public int getRating() {
		return rating;
	}
	public void setRating(int rating) {
		this.rating = rating;
	}
	public String getContents() {
		return contents;
	}
	public void setContents(String contents) {
		this.contents = contents;
	}
	public int getRecommend() {
		return recommend;
	}
	public void setRecommend(int recommend) {
		this.recommend = recommend;
	}
	@Override
	public String toString() {
		return "FoodReview [reviewId=" + reviewId + ", orderId=" + orderId + ", resId=" + resId + ", userId=" + userId
				+ ", reviewDate=" + reviewDate + ", rating=" + rating + ", contents=" + contents + ", recommend="
				+ recommend + "]";
	}
	
	
}
