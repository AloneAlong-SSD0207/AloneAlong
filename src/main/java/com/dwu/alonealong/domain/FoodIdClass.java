package com.dwu.alonealong.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.io.Serializable;

@Getter
@Setter
public class FoodIdClass implements Serializable {

    @Column(name="order_id")
    private String orderId;
    @Column(name="food_id")
    private long foodId;
}
