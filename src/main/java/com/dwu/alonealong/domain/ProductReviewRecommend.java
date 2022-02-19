package com.dwu.alonealong.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name="product_review_recommend")
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "PRRECOMMEND_SEQ_GEN"
        , sequenceName = "PRRECOMMEND_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
public class ProductReviewRecommend implements Serializable {
    /* Private Fields */
    @Id
    @Column(name="id")
    @GeneratedValue( strategy = GenerationType.SEQUENCE, generator = "PRRECOMMEND_SEQ_GEN")
    private int id;

    @Column(name="review_id")
    private long reviewId;

    @Column(name="RECOMMEND_USER")
    private String userId;
}