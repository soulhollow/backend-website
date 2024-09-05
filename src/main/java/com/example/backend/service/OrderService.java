package com.example.backend.service;
import com.example.backend.dto.OrderDTO;
import com.example.backend.dto.OrderItemDTO;
import com.example.backend.model.Order;
import com.example.backend.model.OrderItem;
import com.example.backend.model.User;
import com.example.backend.repository.OrderItemRepository;
import com.example.backend.repository.OrderRepository;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public Order createOrder(OrderDTO orderDTO) {
        Optional<User> user = userRepository.findById(orderDTO.getUserId());
        if (user.isPresent()) {
            Order order = new Order();
            order.setUser(user.get());
            order.setOrderStatus("PENDING");
            order.setTotalAmount(calculateTotalAmount(orderDTO.getOrderItems()));

            Order savedOrder = orderRepository.save(order);

            // Speichern der Bestellartikel (OrderItems)
            for (OrderItemDTO itemDTO : orderDTO.getOrderItems()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrder(savedOrder);
                orderItem.setProduct(productRepository.findById(itemDTO.getProductId()).orElseThrow());
                orderItem.setQuantity(itemDTO.getQuantity());
                orderItem.setPrice(orderItem.getProduct().getPrice());
                orderItemRepository.save(orderItem);
            }
            return savedOrder;
        }
        throw new RuntimeException("Benutzer nicht gefunden");
    }

    public Optional<Order> updateOrderStatus(Long id, String status) {
        return orderRepository.findById(id).map(order -> {
            order.setOrderStatus(status);
            return orderRepository.save(order);
        });
    }

    public boolean deleteOrder(Long id) {
        return orderRepository.findById(id).map(order -> {
            orderRepository.delete(order);
            return true;
        }).orElse(false);
    }

    private BigDecimal calculateTotalAmount(List<OrderItemDTO> orderItems) {
        return orderItems.stream()
                .map(item -> productRepository.findById(item.getProductId())
                        .orElseThrow(() -> new RuntimeException("Produkt nicht gefunden"))
                        .getPrice().multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
