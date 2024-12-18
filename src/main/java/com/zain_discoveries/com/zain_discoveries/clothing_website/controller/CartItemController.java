package com.zain_discoveries.com.zain_discoveries.clothing_website.controller;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.CartItemException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.UserException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Cart;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.CartItem;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.CartItemService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.CartService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart_items")
public class CartItemController {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private CartItemService cartItemService;

    public CartItemController(UserService userService, CartItemService cartItemService, CartService cartService) {
        this.cartService = cartService;
        this.cartItemService = cartItemService;
        this.userService = userService;
    }

//    public ResponseEntity<CartItem> createCartItem(@RequestBody C)

    @DeleteMapping("/{id}")
    public ResponseEntity<String> removeCart(@PathVariable("id") Long itemId, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = null;
        String res = "";
        try {
            user = userService.findUserProfileByJwtToken(jwt);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            Cart cart = cartService.findUserCart(user.getId());
            if (cart == null) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            res = cartItemService.removeCartItem(cart.getId(), user.getId());
        } catch (Exception e) {
            throw e;
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PutMapping("/{cartItemId}")
    @Description("update cart item id")
    public ResponseEntity<CartItem> updateCartItem(@RequestBody CartItem cartItem, @PathVariable Long cartItemId, @RequestHeader String jwt) throws UserException, CartItemException {
        User user = userService.findUserProfileByJwtToken(jwt);
        if (user == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CartItem updatedCartItem = cartItemService.updateCartItem(user.getId(),cartItemId,cartItem);
        return new ResponseEntity<>(updatedCartItem,HttpStatus.OK);
    }
}
