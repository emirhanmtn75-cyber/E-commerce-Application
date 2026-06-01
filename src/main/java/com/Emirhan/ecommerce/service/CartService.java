package com.Emirhan.ecommerce.service;

import com.Emirhan.ecommerce.dto.request.AddToCartRequest;
import com.Emirhan.ecommerce.dto.response.CartItemResponse;

import com.Emirhan.ecommerce.entity.*;
import com.Emirhan.ecommerce.repository.*;
import com.Emirhan.ecommerce.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public void addToCart(AddToCartRequest request) {
        User user = getAuthenticatedUser();
        request.setUserId(user.getId());

        Cart cart = cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem cartItem = cartItemRepository
                .findByCartAndProduct(cart, product)
                .orElse(null);

        if (cartItem != null) {
            cartItem.setQuantity(cartItem.getQuantity() + request.getQuantity());
        } else {
            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(request.getQuantity());
        }

        cartItemRepository.save(cartItem);
    }

    public List<CartItemResponse> getCurrentUserCartItems() {
        User user = getAuthenticatedUser();

        Cart cart = cartRepository.findByUser(user)
                .orElse(null);

        if (cart == null) {
            return List.of();
        }

        return mapCartItems(cart);
    }

    // 📄 Sepeti listeleme
    public List<CartItemResponse> getCartItems(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        return mapCartItems(cart);
    }

    private List<CartItemResponse> mapCartItems(Cart cart) {
        return cartItemRepository.findByCart(cart)
                .stream()
                .map(item -> new CartItemResponse(
                        item.getId(),
                        item.getProduct().getId(),
                        item.getProduct().getTitle(),
                        item.getProduct().getPrice(),
                        item.getProduct().getImageUrl(),
                        item.getQuantity()
                ))
                .collect(Collectors.toList());
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (authentication == null || authentication.getName() == null) {
            throw new RuntimeException("User not authenticated");
        }

        User user = userRepository.findByEmail(authentication.getName());

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        return user;
    }

    // ➖ Sepetten ürün silme
    public void removeCartItem(Long cartItemId) {
        cartItemRepository.deleteById(cartItemId);
    }

    public void updateCartItemQuantity(Long cartItemId, int quantity) {
        if (quantity < 1) {
            throw new RuntimeException("Quantity must be greater than zero");
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
    }
}
