package com.Emirhan.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderItemResponse {
    private String productName;
    private int quantity;
    private double price;
}
