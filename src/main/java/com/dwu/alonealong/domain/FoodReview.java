package com.dwu.alonealong.domain;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.*;
import lombok.*;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name="food_review")
@SequenceGenerator(
		name = "REV_SEQ_GENERATOR"
		, sequenceName = "REVIEWID_SEQ"
		, initialValue = 1
		, allocationSize = 1
)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class FoodReview implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REV_SEQ_GENERATOR")
	@Column(name="review_id")
	long reviewId;
	@Column(name="order_id")
	String orderId;
	@Column(name="res_id")
	long resId;
	@Column(name="user_id")
	String userId;
	@Column(name="review_date")
	LocalDateTime reviewDate = LocalDateTime.now();
	@Column(name="review_rating")
	int rating;
	@Column(name="review_contents")
	String contents;
	@Column(name="review_recommend")
	int recommend;
	
	@Transient
	String userNickName;

	@Override
	public String toString() {
		return "FoodReview [reviewId=" + reviewId + ", orderId=" + orderId + ", resId=" + resId + ", userId=" + userId
				+ ", reviewDate=" + reviewDate + ", rating=" + rating + ", contents=" + contents + ", recommend="
				+ recommend + "]";
	}
	
	
}
