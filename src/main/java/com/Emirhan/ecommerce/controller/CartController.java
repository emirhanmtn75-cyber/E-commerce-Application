package com.Emirhan.ecommerce.controller;

import com.Emirhan.ecommerce.dto.request.AddToCartRequest;
import com.Emirhan.ecommerce.dto.response.CartItemResponse;
import com.Emirhan.ecommerce.dto.request.AddToCartRequest;
import com.Emirhan.ecommerce.service.CartService;
import com.Emirhan.ecommerce.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    // ➕ Sepete ekle
    @PostMapping("/add")
    public void addToCart(@RequestBody AddToCartRequest request) {
        cartService.addToCart(request);
    }

    // 📄 Sepeti listele
    @GetMapping("/{userId}")
    public List<CartItemResponse> getCart(@PathVariable Long userId) {
        return cartService.getCartItems(userId);
    }

    // ➖ Sepetten sil
    @DeleteMapping("/remove/{cartItemId}")
    public void removeItem(@PathVariable Long cartItemId) {
        cartService.removeCartItem(cartItemId);
    }
}
