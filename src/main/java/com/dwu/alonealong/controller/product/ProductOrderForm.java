package com.dwu.alonealong.controller.product;

import java.util.List;

import com.dwu.alonealong.domain.Payment;
import com.dwu.alonealong.domain.ProductLineItem;
import com.dwu.alonealong.domain.ProductOrder;
import com.dwu.alonealong.domain.User;

public class ProductOrderForm {
	private int totalPrice;
	private User orderUser;
	private User shipUser;
	private Payment payment;
	private String type;
	private List<ProductLineItem> orderList;
	
	private final ProductOrder order = new ProductOrder();
	
	public ProductOrder getOrder(){
		return order;
	}
	
	public void initProductOrder(User user, Payment payment) {
		this.orderUser = user;
		this.payment = payment;
	}

	public User getOrderUser() { return orderUser; }
	public void setOrderUser(User orderUser) { this.orderUser = orderUser; }

	public Payment getPayment() { return payment; }
	public void setPayment(Payment payment) { this.payment = payment; }
	
	public int getTotalPrice() { return totalPrice; }
	public void setTotalPrice(int totalPrice) { this.totalPrice = totalPrice; }

	public String getType() { return type; }
	public void setType(String type) { this.type = type; }
}
