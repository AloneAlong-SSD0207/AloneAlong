package com.dwu.alonealong.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name="cartitem")
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "CARTITEM_SEQ_GEN"
        , sequenceName = "CARTITEM_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
public class CartItem implements Serializable {
  /* Private Fields */
  @Id
  @Column(name="cartitem_id")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CARTITEM_SEQ_GEN")
  private long cartItemId;
  @Column(name="user_id")
  private String userId;
  @Column(name="product_id")
  private String productId;
  @Column(name="quantity")
  private int quantity;
  @OneToOne
  @JoinColumn(name="product_id", insertable = false, updatable = false)
  private Product product;

  /* Public methods */
  public int getUnitPrice() {
	  int totalPrice = 0;
	  totalPrice += quantity * product.getProductPrice();
	  return totalPrice;
  }
  public CartItem(String userId, String productId, int quantity){
    this.userId = userId;
    this.productId = productId;
    this.quantity = quantity;
  }
}
