package com.dwu.alonealong.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@SuppressWarnings("serial")
@Entity
@Table(name="TOGETHER_ORDER")
@Getter
@Setter
@NoArgsConstructor
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

	public TogetherOrder(String orderId, String togetherId) {
		this.orderId = orderId;
		this.togetherId = togetherId;
	}
	
}
