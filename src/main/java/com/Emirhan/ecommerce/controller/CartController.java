package com.Emirhan.ecommerce.controller;

import com.Emirhan.ecommerce.dto.request.AddToCartRequest;
import com.Emirhan.ecommerce.dto.request.UpdateCartItemRequest;
import com.Emirhan.ecommerce.dto.response.CartItemResponse;
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

    @GetMapping
    public List<CartItemResponse> getCurrentUserCart() {
        return cartService.getCurrentUserCartItems();
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

    @PutMapping("/update/{cartItemId}")
    public void updateItem(@PathVariable Long cartItemId, @RequestBody UpdateCartItemRequest request) {
        cartService.updateCartItemQuantity(cartItemId, request.getQuantity());
    }
}
