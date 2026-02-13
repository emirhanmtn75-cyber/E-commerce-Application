package com.Emirhan.ecommerce.controller;

import com.Emirhan.ecommerce.dto.response.OrderDetailResponse;
import com.Emirhan.ecommerce.dto.response.OrderResponse;
import com.Emirhan.ecommerce.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/{userId}")
    public void createOrder(@PathVariable Long userId) {
        orderService.createOrder(userId);
    }
    @GetMapping("/user/{userId}")
    public List<OrderResponse> getUserOrders(@PathVariable Long userId){
      return   orderService.getOrdersByUser(userId);

    }

    @GetMapping("/{orderId}")
    public OrderDetailResponse getOrderDetail(@PathVariable Long orderId){
        return orderService.getOrderDetail(orderId);
    }

}
