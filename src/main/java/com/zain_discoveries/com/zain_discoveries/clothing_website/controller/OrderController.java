package com.zain_discoveries.com.zain_discoveries.clothing_website.controller;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.OrderException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.UserException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Address;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Order;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.request.OrderRequest;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.OrderService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private OrderService orderService;
    @Autowired
    private UserService userService;

    public OrderController(OrderService orderService,UserService userService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @PostMapping("/create")
    public ResponseEntity<Order> createOrder(@RequestBody Address shippingAddress, @RequestHeader("Authorization") String jwt) throws UserException {
        User user = userService.findUserProfileByJwtToken(jwt);
        if (user == null){
            throw new IllegalArgumentException("no user found");
        }
        Order order = orderService.createOrder(user,shippingAddress);
        return new ResponseEntity<>(order,HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long orderId, @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findUserProfileByJwtToken(jwt);
        if (user == null){
            throw new IllegalArgumentException("user not found with the given userId");
        }
        Order order = orderService.findOrderById(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/orderHistory/{userId}")
    public ResponseEntity<List<Order>> getUserOrderHistory(@PathVariable Long userId, @RequestHeader("Authorization") String jwt) throws OrderException, UserException {
        User user = userService.findUserProfileByJwtToken(jwt);
        List<Order> order = orderService.usersOrderHistory(userId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/placeOrder/{orderId}")
    public ResponseEntity<Order> placeOrder(@PathVariable Long orderId) throws OrderException {
        Order order = orderService.placeOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/confirmOrder/{orderId}")
    public ResponseEntity<Order> confirmOrder(@PathVariable Long orderId) throws OrderException {
        Order order = orderService.confirmOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/shippedOrder/{orderId}")
    public ResponseEntity<Order> shippedOrder(@PathVariable Long orderId) throws OrderException {
        Order order = orderService.shippedOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/deliveredOrder/{orderId}")
    public ResponseEntity<Order> deliveredOrder(@PathVariable Long orderId) throws OrderException {
        Order order = orderService.deliveredOrder(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PutMapping("/cancelledOrder/{orderId}")
    public ResponseEntity<Order> cancelledOrder(@PathVariable Long orderId) throws OrderException {
        return ResponseEntity.ok(orderService.cancelledOrder(orderId));
    }

    @GetMapping()
    public ResponseEntity<List<Order>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @DeleteMapping("/removeOrder/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) throws OrderException {
        orderService.deleteOrder(orderId);
        return new ResponseEntity<>("Your order is successfully deleted", HttpStatus.OK);
    }
}
