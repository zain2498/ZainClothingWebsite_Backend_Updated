package com.zain_discoveries.com.zain_discoveries.clothing_website.service;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.OrderException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Address;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Order;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;

import java.util.List;

public interface OrderService {
    public Order createOrder(User user, Address shippingAddress);

    public Order findOrderById(Long id) throws OrderException;

    public List<Order> usersOrderHistory(Long userId) throws OrderException;

    public Order placeOrder(String orderId) throws OrderException;

    public Order confirmOrder(String orderId) throws OrderException;

    public Order shippedOrder(String orderId) throws OrderException;

    public Order deliveredOrder(String orderId) throws OrderException;

    public Order cancelledOrder(String orderId) throws OrderException;

    public List<Order> getAllOrders();

    public void deleteOrder(String orderId) throws OrderException;
}

