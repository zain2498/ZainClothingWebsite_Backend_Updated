package com.zain_discoveries.com.zain_discoveries.clothing_website.service.impl;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.OrderException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Address;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Order;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.CartRepository;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.CartService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.OrderService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImplementation implements OrderService {

    private CartService cartService;
    private CartRepository cartRepository;
    private ProductService productService;

    public OrderServiceImplementation(CartService cartService, CartRepository cartRepository, ProductService productService) {
        this.productService = productService;
        this.cartRepository = cartRepository;
        this.cartService = cartService;
    }

    @Override
    public Order createOrder(User user, Address shippingAddress) {
        return null;
    }

    @Override
    public Order findOrderById(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<Order> usersOrderHistory(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order placeOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order confirmOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order shippedOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order deliveredOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public Order cancelledOrder(Long orderId) throws OrderException {
        return null;
    }

    @Override
    public List<Order> getAllOrders() {
        return null;
    }

    @Override
    public void deleteOrder(Long orderId) throws OrderException {

    }
}
