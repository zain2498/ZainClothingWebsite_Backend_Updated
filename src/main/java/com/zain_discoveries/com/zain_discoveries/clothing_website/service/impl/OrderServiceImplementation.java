package com.zain_discoveries.com.zain_discoveries.clothing_website.service.impl;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.OrderException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.*;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.enums.OrderStatus;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.CartRepository;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.OrderRepository;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.CartService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.OrderService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.ProductService;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService {
    private OrderRepository orderRepository;

    public OrderServiceImplementation(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setShippingAddress(shippingAddress);
        order.setOrderDate(LocalDate.now().atStartOfDay());
        order.setOrderStatus(OrderStatus.CREATED);
        order.setTotalPrice(0);
        order.setTotalItem(0);
        return orderRepository.save(order);
    }

    @Override
    public Order findOrderById(Long oId) throws OrderException {
        return orderRepository.findById(oId).orElseThrow(() -> new OrderException("Order not found with the given orderId"));
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) throws OrderException {
        List<Order> orders = orderRepository.findByUserId(userId);
        if (orders.isEmpty()) {
            throw new IllegalArgumentException("There are no orders found against the given userId");
        }
        return orders;
    }

    @Override
    public Order placeOrder(String orderId) throws OrderException {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null){
            throw new IllegalArgumentException("No order found against the given orderId");
        }
        order.setOrderStatus(OrderStatus.PLACED);
        return orderRepository.save(order);
    }

    @Override
    public Order confirmOrder(String orderId) throws OrderException {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null){
            throw new IllegalArgumentException("No order found against the given orderId");
        }
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(String orderId) throws OrderException {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null){
            throw new IllegalArgumentException("No order found against the given orderId");
        }
        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(String orderId) throws OrderException {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null){
            throw new IllegalArgumentException("No order found against the given orderId");
        }
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setDeliveryDate(LocalDateTime.now().plusDays(3));
        return orderRepository.save(order);
    }

    @Override
    public Order cancelledOrder(String orderId) throws OrderException {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null){
            throw new IllegalArgumentException("No order found against the given orderId");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> order = orderRepository.findAll();
        return order;
    }

    @Override
    public void deleteOrder(String orderId) throws OrderException {
        Order order = orderRepository.findByOrderId(orderId);
        if (order == null){
            throw new IllegalArgumentException("No order found against the given orderId");
        }
        orderRepository.delete(order);
    }
}
