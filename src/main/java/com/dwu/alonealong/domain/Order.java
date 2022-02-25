package com.dwu.alonealong.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.*;
import lombok.*;
@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name="orderinfo")
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class Order implements Serializable{
	@Id
	@Column(name="order_id")
	private String orderId;
	@Column(name="order_date")
	private String orderDate;
	@Column(name="total_price")
	private int totalPrice;
	@Column(name="order_status")
	private String status;
	@Column(name="user_id")
	private String userId;
	@Column(name="card_num")
	private String cardNum;
	@Column(name="card_date")
	private String cardDate;
	@Column(name="card_name")
	private String cardName;
	@OneToOne
	@JoinColumn(name="order_id", insertable = false, updatable = false)
	ProductOrder productOrder;
	@Transient
	private String cardYear;
	@Transient
	private String cardMonth;
	@Transient
	private String cardCVC;

	@OneToOne
	@JoinColumn(name="order_id", insertable = false, updatable = false)
	FoodOrder foodOrder;

	public int getShippingFee() {
		return Product.SHIPPING_FEE;
	}
	public Order saveSet(Order order, String orderId) {
		setOrderId(orderId);
		setStatus("결제완료");
		setCardDate(this.getCardMonth() + '/' + this.getCardYear());
		return order;
	}

	public Order(String orderId, int totalPrice, String status, String userId, String cardNum, String cardDate, String cardName) {
		this.orderId = orderId;
		this.totalPrice = totalPrice;
		this.status = status;
		this.userId = userId;
		this.cardNum = cardNum;
		this.cardDate = cardDate;
		this.cardName = cardName;
	}
}
