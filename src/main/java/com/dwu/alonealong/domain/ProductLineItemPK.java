package com.dwu.alonealong.domain;

import lombok.Data;

import java.io.Serializable;

@SuppressWarnings("serial")
@Data
public class ProductLineItemPK implements Serializable {
    private String orderId;
    private String productId;
}
