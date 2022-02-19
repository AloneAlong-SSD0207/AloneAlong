package com.dwu.alonealong.domain;

import java.io.Serializable;

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
	
	public Order() {}

	public Order(String orderId, String orderDate, int totalPrice, String status, String userId, String cardNum,
			String cardDate, String cardName) {
		super();
		this.orderId = orderId;
		this.orderDate = orderDate;
		this.totalPrice = totalPrice;
		this.status = status;
		this.userId = userId;
		this.cardNum = cardNum;
		this.cardDate = cardDate;
		this.cardName = cardName;
	}

	public Order(String orderId, int totalPrice, String status, String userId, String cardNum, String cardDate,
			String cardName) {
		super();
		this.orderId = orderId;
		this.totalPrice = totalPrice;
		this.status = status;
		this.userId = userId;
		this.cardNum = cardNum;
		this.cardDate = cardDate;
		this.cardName = cardName;
	}

	public String getOrderId() {return orderId;}
	public void setOrderId(String orderId) {this.orderId = orderId;}
	
	public String getOrderDate() {return orderDate;}
	public void setOrderDate(String orderDate) {this.orderDate = orderDate;}
	
	public int getTotalPrice() {return totalPrice;}
	public void setTotalPrice(int totalPrice) {this.totalPrice = totalPrice;}
	
	public String getStatus() {return status;}
	public void setStatus(String status) {this.status = status;}
	
	public String getUserId() {return userId;}
	public void setUserId(String userId) {this.userId = userId;}
	
	public String getCardName() {return cardName;}
	public void setCardName(String cardName) {this.cardName = cardName;}
	
	public String getCardNum() {return cardNum;}
	public void setCardNum(String cardNum) {this.cardNum = cardNum;}
	
	public String getCardDate() {return cardDate;}
	public void setCardDate(String cardDate) {this.cardDate = cardDate;}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", totalPrice=" + totalPrice + ", status="
				+ status + ", userId=" + userId + ", cardNum=" + cardNum + ", cardDate=" + cardDate + ", cardName="
				+ cardName + "]";
	}
	
}
