package com.dwu.alonealong.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@ToString
@Table(name="product_review")
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "PRODUCTREVIEW_SEQ_GEN"
        , sequenceName = "PRODUCTREVIEW_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
public class ProductReview implements Serializable {
  public final static int MAX_CONTENT_SIZE = 300;

  /* Private Fields */
  @Id
  @Column(name="review_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PRODUCTREVIEW_SEQ_GEN")
  private long reviewId;

  @Column(name="product_id")
  private String productId;
  @Column(name="user_id")
  private String userId;
  @CreationTimestamp
  @Column(name="review_date")
  private Date reviewDate;
  @Column(name="review_rating")
  private int rating;
  @Column(name="review_contents")
  private String reviewContents;
  @Column(name="review_recommend")
  private int recommend;
  @Transient
  private boolean checkRecommend;
  @ManyToOne
  @JoinColumn(name="user_id", insertable = false, updatable = false)
  private UserVO user;

  /* Public Methods */
  public void increaseRecommend() {
	  this.recommend++;
  }
  public void decreaseRecommend() {
	  this.recommend--;
  }

  public boolean getCheckRecommend() { return checkRecommend; }
}