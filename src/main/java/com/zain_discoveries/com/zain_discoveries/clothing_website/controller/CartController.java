package com.zain_discoveries.com.zain_discoveries.clothing_website.controller;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.ProductException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.UserException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Cart;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.request.AddItemRequest;
import com.zain_discoveries.com.zain_discoveries.clothing_website.response.ApiResponse;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.CartService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.UserService;
import jdk.jfr.Name;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
@Name("Cart Management")
@Description("find user cart, add item to cart")
public class CartController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    public CartController(UserService userService, CartService cartService) {
        this.userService = userService;
        this.cartService = cartService;
    }

    @PostMapping("/addToCart")
    @Description("Add item to cart")
    public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest request, @RequestHeader("Authorization") String jwt) {
        ApiResponse res = null;
        try {
            User user = userService.findUserProfileByJwtToken(jwt);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            cartService.addToCart(user.getId(), request);
            res = new ApiResponse();
            res.setMessage("item is added to cart");
            res.setStatus(true);
        } catch (UserException | ProductException e) {
            throw new RuntimeException(e);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
