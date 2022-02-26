package com.dwu.alonealong.domain;

import javax.persistence.*;
import java.io.Serializable;
import lombok.Setter;
import lombok.Getter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@SuppressWarnings("serial")
@Getter
@Setter
@Entity
@Table(name="product")
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {
    public final static int SHIPPING_FEE = 3000;
    public final static int FREE_SHIPPING_PRICE = 30000;

    /* Private Fields */
    @Id
    @Column(name="product_id")
    private String productId;

    @Column(name="pc_id")
    private String pcId; //productCategoryId
    @Column(name="product_name")
    private String productName;
    @Column(name="product_info")
    private String productInfo;
    @Column(name="product_date")
    private String productDate;
    @Column(name="product_price")
    private int productPrice;
    @Column(name="product_sales")
    private int productSales;
    @Column(name="product_stock")
    private int productStock;
    @Transient
    private int quantity;

    public int getShippingFee() {
      return SHIPPING_FEE;
    }
    public int getFreeShippingPrice() {
        return FREE_SHIPPING_PRICE;
    }

    /*Additional Methods*/
    public boolean isInStock() {
      if (productStock == 0) return true;
      return false;
    }
    public int getUnitPrice() {
      int unitPrice = 0;
      unitPrice += quantity * productPrice;
      if (unitPrice < FREE_SHIPPING_PRICE) {
          unitPrice += SHIPPING_FEE;
      }
      return unitPrice;
    }
    public int getTotalPrice(String quantity) {
      try {
          int totalPrice = 0;
          totalPrice += Integer.parseInt(quantity) * productPrice;
          if (totalPrice < FREE_SHIPPING_PRICE) {
              totalPrice += SHIPPING_FEE;
          }
          return totalPrice;
      } catch(NumberFormatException e) {
          return 0;
      }
    }
}
