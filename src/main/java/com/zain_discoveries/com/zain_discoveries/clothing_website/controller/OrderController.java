package com.zain_discoveries.com.zain_discoveries.clothing_website.controller;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.OrderException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Address;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Order;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/orders")
public class OrderController {

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody User user, @RequestBody Address shippingAddress) {
        Order order = orderService.createOrder(user, shippingAddress);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long orderId) throws OrderException {
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/orderHistory/{userId}")
    public ResponseEntity<List<Order>> getUserOrderHistory(@PathVariable Long userId) throws OrderException {
        List<Order> order = orderService.usersOrderHistory(userId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/placeOrder/{orderId}")
    public ResponseEntity<Order> placeOrder(@PathVariable String orderId) throws OrderException {
        Order order = orderService.placeOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/confirmOrder/{orderId}")
    public ResponseEntity<Order> confirmOrder(@PathVariable String orderId) throws OrderException {
        Order order = orderService.confirmOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/shippedOrder/{orderId}")
    public ResponseEntity<Order> shippedOrder(@PathVariable String orderId) throws OrderException {
        Order order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/deliveredOrder/{orderId}")
    public ResponseEntity<Order> deliveredOrder(@PathVariable String orderId) throws OrderException {
        Order order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/cancelledOrder/{orderId}")
    public ResponseEntity<Order> cancelledOrder(@PathVariable String orderId) throws OrderException {
        return ResponseEntity.ok(orderService.cancelledOrder(orderId));
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @DeleteMapping("/removeOrder/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable String orderId) throws OrderException {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>("Your order is successfully deleted", HttpStatus.OK);
    }
}
