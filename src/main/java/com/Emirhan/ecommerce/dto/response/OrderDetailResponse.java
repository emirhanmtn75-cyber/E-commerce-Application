package com.Emirhan.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
@Getter
@Setter
public class OrderDetailResponse {
    private Long orderId;
    private LocalDateTime orderDate;
    private double totalPrice;
    private List<OrderItemResponse> items;
}
