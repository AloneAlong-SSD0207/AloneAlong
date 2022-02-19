package com.dwu.alonealong.domain;

import lombok.*;
import org.hibernate.annotations.GenerationTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@ToString
@Table(name="product_order")
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(
        name = "PRODUCTORDERID_SEQ_GEN"
        , sequenceName = "PRODUCTORDERID_SEQ"
        , initialValue = 1
        , allocationSize = 1
)
public class ProductOrder implements Serializable {
  @Id
  @Column(name="order_id")
  private String orderId;
  @Column(name="ship_name")
  private String shipName;
  @Column(name="ship_phone")
  private String shipPhone;
  @Column(name="ship_email")
  private String shipEmail;
  @Column(name="SHIP_ZIP")
  private String shipZip;
  @Column(name="ship_address")
  private String shipAddress;
  @Column(name="ship_address2")
  private String shipAddress2;
  @Transient
  private String shipPhone1;
  @Transient
  private String shipPhone2;
  @Transient
  private String shipPhone3;
  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name="order_id", insertable = true, updatable = true)
  Order order;
//  @OneToMany
//  @JoinColumn(name="order_id", insertable = false, updatable = false)
//  ProductLineItem productOrder;
  @Transient
  private List<ProductLineItem> lineItems;

  public void setShipPhoneByForm(){
    this.shipPhone = this.shipPhone1 + '-' + this.shipPhone2 + '-' + this.shipPhone3;
  }
}