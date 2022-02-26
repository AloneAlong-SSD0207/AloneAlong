package com.dwu.alonealong.domain;

import javax.persistence.Column;
import java.io.Serializable;

public class FoodIdClass implements Serializable {

    @Column(name="order_id")
    private String orderId;
    @Column(name="food_id")
    private long foodId;
}
