package com.dwu.alonealong.domain;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name="TOGETHER_ORDER")
public class TogetherOrder implements Serializable {
	@Id
	@Column(name = "order_id")
	private String orderId;
	@Column(name = "tog_id")
	private String togetherId;

	@ManyToOne
	@JoinColumn(name="order_id", insertable=false, updatable=false)
	private Order order;

	@ManyToOne
	@JoinColumn(name="tog_id", insertable=false, updatable=false)
	private Together together;
	
	public TogetherOrder() {}

	public TogetherOrder(String orderId, String togetherId) {
		this.orderId = orderId;
		this.togetherId = togetherId;
	}
	
	public String getOrderId() {return orderId;}
	public void setOrderId(String orderId) {this.orderId = orderId;}

	public String getTogetherId() {return togetherId;}
	public void setTogetherId(String togetherId) {this.togetherId = togetherId;}
	
	public Order getOrder() {return order;}
	public void setOrder(Order order) {this.order = order;}

	public Together getTogether() {return together;}
	public void setTogether(Together together) {this.together = together;}
	
}
