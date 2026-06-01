package com.Emirhan.ecommerce.service;

import com.Emirhan.ecommerce.dto.response.OrderDetailResponse;
import com.Emirhan.ecommerce.dto.response.OrderItemResponse;
import com.Emirhan.ecommerce.dto.response.OrderResponse;
import com.Emirhan.ecommerce.entity.*;
import com.Emirhan.ecommerce.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Transactional
    public void createOrderForCurrentUser() {
        createOrder(getAuthenticatedUser().getId());
    }

    @Transactional
    public void createOrder(Long userId) {
        // 1️⃣ User bul
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        // 2️⃣ Cart bul
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new RuntimeException("Cart not found"));
        List<CartItem> cartItems = cartItemRepository.findByCart(cart);
        // 3️⃣ Cart boş mu?
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }
        // 4️⃣ Order oluştur
        Order order = new Order();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        double totalPrice = 0;
        // 5️⃣ CartItem -> OrderItem
        for (CartItem cartItem : cartItems) {
            Product product = cartItem.getProduct();
            // stok kontrolü
            if (product.getStock() < cartItem.getQuantity()) {
                throw new RuntimeException(
                        "Not enough stock for product: " + product.getTitle()
                );
            }
            // stok düş
            product.setStock(product.getStock() - cartItem.getQuantity());
            productRepository.save(product);
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(product);
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setPrice(product.getPrice());
            totalPrice += product.getPrice() * cartItem.getQuantity();
            orderItemRepository.save(orderItem);
        }
        // 6️⃣ total price set
        order.setTotalPrice(totalPrice);
        // 7️⃣ Order save
        orderRepository.save(order);
        // 8️⃣ Sepeti temizle
        cartItemRepository.deleteAll(cartItems);
    }
    public List<OrderResponse> getOrdersByUser(Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        List<Order> orders = orderRepository.findByUser(user);

        return orders.stream()
                .map(this::mapToResponse)
                .toList();
    }


    private OrderResponse mapToResponse(Order Order) {
        OrderResponse response = new OrderResponse();
      response.setOrderId(Order.getId());
      response.setOrderDate(Order.getOrderDate());
      response.setTotalPrice(Order.getTotalPrice());
        return response;
    }
    public OrderDetailResponse getOrderDetail(Long orderId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItemResponse> items = order.getOrderItems()
                .stream()
                .map(this::mapToItemResponse)
                .toList();

        OrderDetailResponse response = new OrderDetailResponse();
        response.setOrderId(order.getId());
        response.setOrderDate(order.getOrderDate());
        response.setTotalPrice(order.getTotalPrice());
        response.setItems(items);

        return response;
    }

    private OrderItemResponse mapToItemResponse(OrderItem OrderItem) {
      OrderItemResponse orderItemResponse=new OrderItemResponse();
      orderItemResponse.setProductName(OrderItem.getProduct().getTitle());
      orderItemResponse.setQuantity(OrderItem.getQuantity());
      orderItemResponse.setPrice(OrderItem.getPrice());
      return orderItemResponse;
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
}
