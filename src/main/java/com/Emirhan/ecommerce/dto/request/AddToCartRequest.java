package com.Emirhan.ecommerce.dto.request;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddToCartRequest {

    private Long userId;
    private Long productId;
    private int quantity;
}
