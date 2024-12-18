package com.zain_discoveries.com.zain_discoveries.clothing_website.service.impl;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.OrderException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.*;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.enums.OrderStatus;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.*;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.CartService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.OrderItemService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.OrderService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.ProductService;
import org.aspectj.weaver.ast.Or;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImplementation implements OrderService {
    private OrderRepository orderRepository;
    private CartRepository cartRepository;
    private CartService cartService;
    private ProductService productService;
    private AddressRepository addressRepository;
    private OrderItemService orderItemService;
    private OrderItemRepository orderItemRepository;
    private UserRepository userRepository;

    public OrderServiceImplementation(OrderRepository orderRepository, CartService cartService, CartRepository cartRepository, ProductService productService, OrderItemRepository orderItemRepository, OrderItemService orderItemService, AddressRepository addressRepository,UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.cartRepository = cartRepository;
        this.productService = productService;
        this.orderItemService = orderItemService;
        this.orderItemRepository = orderItemRepository;
        this.addressRepository = addressRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        shippingAddress.setUser(user);
        Address address = addressRepository.save(shippingAddress);
        user.getAddresses().add(address);
        userRepository.save(user);

        Cart cart = cartRepository.findCartByUserId(user.getId());
        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem cartItem : cart.getCartItems()){
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(cartItem.getPrice());
            orderItem.setProduct(cartItem.getProduct());
            orderItem.setQuantity(cartItem.getQuantity());
            orderItem.setSize(String.valueOf(cartItem.getSize()));
            orderItem.setUserId(cartItem.getUserId());
            orderItem.setDiscountedPrice(cartItem.getDiscountedPrice());

            OrderItem createdOrderItem = orderItemRepository.save(orderItem);
            orderItems.add(createdOrderItem);
        }

        Order createdOrder = new Order();
        createdOrder.setUser(user);
        createdOrder.setOrderItems(orderItems);
        createdOrder.setTotalPrice(cart.getTotalPrice());
        createdOrder.setTotalDiscountedPrice(cart.getTotalDiscountedPrice());
        createdOrder.setDiscount(cart.getDiscount());
        createdOrder.setTotalItem(cart.getTotalItem());

        createdOrder.setShippingAddress(address);
        createdOrder.setOrderDate(LocalDateTime.now());
        createdOrder.setOrderStatus(OrderStatus.CREATED);
        createdOrder.getPaymentDetails().setStatus("PENDING");
        createdOrder.setCreatedAt(LocalDateTime.now());

        Order savedOrder = orderRepository.save(createdOrder);

        for (OrderItem item : orderItems){
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return savedOrder;
    }

    @Override
    public Order findOrderById(Long oId) throws OrderException {
        return orderRepository.findById(oId).orElseThrow(() -> new OrderException("Order not found with the given orderId"));
    }

    @Override
    public List<Order> usersOrderHistory(Long userId) throws OrderException {
        return orderRepository.getUsersOrders(userId);
    }

    @Override
    public Order placeOrder(Long orderId) throws OrderException {
        Order order = findByOrderId(orderId);
        if (order == null) {
            throw new IllegalArgumentException("No order found against the given orderId");
        }
        order.setOrderStatus(OrderStatus.PLACED);
        return orderRepository.save(order);
    }

    @Override
    public Order confirmOrder(Long orderId) throws OrderException {
        Order order = findByOrderId(orderId);
        if (order == null) {
            throw new IllegalArgumentException("No order found against the given orderId");
        }
        order.setOrderStatus(OrderStatus.CONFIRMED);
        return orderRepository.save(order);
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        Order order = findByOrderId(orderId);
        if (order == null) {
            throw new IllegalArgumentException("No order found against the given orderId");
        }
        order.setOrderStatus(OrderStatus.SHIPPED);
        return orderRepository.save(order);
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        Order order = findByOrderId(orderId);
        if (order == null) {
            throw new IllegalArgumentException("No order found against the given orderId");
        }
        order.setOrderStatus(OrderStatus.DELIVERED);
        order.setDeliveryDate(LocalDateTime.now().plusDays(3));
        return orderRepository.save(order);
    }

    @Override
    public Order cancelledOrder(Long orderId) throws OrderException {
        Order order = findByOrderId(orderId);
        if (order == null) {
            throw new IllegalArgumentException("No order found against the given orderId");
        }
        order.setOrderStatus(OrderStatus.CANCELLED);
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {
        Order order = findByOrderId(orderId);
        orderRepository.deleteById(order.getId());
    }

    public Order findByOrderId(Long orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()){
            return order.get();
        }
        throw new IllegalArgumentException("no order found with the given orderId");
    }
}
