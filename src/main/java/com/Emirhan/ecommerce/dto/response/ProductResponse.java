package com.Emirhan.ecommerce.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse {

    private Long id;
    private String name;
    private String description;
    private double price;
    private int stock;

    private Long categoryId;
    private String categoryName;
}
