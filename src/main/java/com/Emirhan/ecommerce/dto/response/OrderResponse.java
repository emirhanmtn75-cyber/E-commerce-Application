package com.Emirhan.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
@Getter
@Setter
public class OrderResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private double totalPrice;
}
