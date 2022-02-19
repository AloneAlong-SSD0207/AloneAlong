package com.dwu.alonealong.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@ToString
@IdClass(ProductLineItemPK.class)
@Table(name="product_lineitem")
@NoArgsConstructor
@AllArgsConstructor
public class ProductLineItem implements Serializable {
	@Id
	@Column(name = "order_id")
	private String orderId;
	@Id
	@Column(name = "product_id")
	private String productId;
	@Column(name = "unit_price")
	private int unitPrice;
	private int quantity;
	@ManyToOne
	@JoinColumn(name="product_id", insertable = false, updatable = false)
	Product product;
	@ManyToOne
	@JoinColumn(name="order_id", insertable = false, updatable = false)
	ProductOrder productOrder;

	public ProductLineItem(Product product) {
		this.productId = product.getProductId();
		this.quantity = product.getQuantity();
		this.unitPrice = product.getUnitPrice();
		this.product = product;
	}

	public ProductLineItem(CartItem cartItem) {
		this.productId = cartItem.getProductId();
		this.quantity = cartItem.getQuantity();
		this.unitPrice = cartItem.getUnitPrice();
		this.product = cartItem.getProduct();
	}
}
