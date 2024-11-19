package com.zain_discoveries.com.zain_discoveries.clothing_website.service.impl;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.CartItemException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.UserException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.*;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.CartItemRepository;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.CartRepository;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.CartItemService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CartItemServiceImplementation implements CartItemService {

    private CartItemRepository cartItemRepository;
    private CartRepository cartRepository;
    private UserService userService;

    public CartItemServiceImplementation(CartItemRepository cartItemRepository, UserService userService, CartRepository cartRepository){
        this.cartItemRepository = cartItemRepository;
        this.cartRepository = cartRepository;
        this.userService = userService;
    }
    @Override
    public CartItem createCartItem(CartItem cartItem) {
        cartItem.setQuantity(1);
        cartItem.setPrice(cartItem.getProduct().getPrice() * cartItem.getQuantity());
        cartItem.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()*cartItem.getQuantity());

        return cartItemRepository.save(cartItem);
    }

    @Override
    public CartItem updateCartItem(Long userId, Long id, CartItem cartItem) throws CartItemException, UserException {
        CartItem item = findCartItemById(id);
        User user = userService.findUserById(userId);
        if(user.getId().equals(userId)){
            item.setQuantity(cartItem.getQuantity());
            item.setPrice(cartItem.getProduct().getPrice()*cartItem.getQuantity());
            item.setDiscountedPrice(cartItem.getProduct().getDiscountedPrice()* cartItem.getQuantity());
        }
        return cartItemRepository.save(item);
    }

    @Override
    public CartItem isCartItemExist(Cart cart, Product product, int size, Long userId) {
        CartItem cartItem = cartItemRepository.isCartItemExist(cart,product,size,userId);
        return cartItem;
    }

    @Override
    public void removeCartItem(Long cartItemId, Long userId) throws UserException, CartItemException {
        CartItem cartItem = findCartItemById(cartItemId);
        User user = userService.findUserById(userId);
        User cartItemUser = userService.findUserById(cartItem.getUserId());

        if(user.getId().equals(cartItemUser.getId())){
            cartItemRepository.delete(cartItem);
        }else {
            throw new CartItemException("You cannot remove other user's cart item");
        }
    }

    @Override
    public CartItem findCartItemById(Long cartItemId) throws CartItemException {
        Optional<CartItem> cartItem = cartItemRepository.findById(cartItemId);
        if (cartItem.isPresent()){
            return cartItem.get();
        }
        throw new CartItemException("There is no cartItem found with the given cartItemId");
    }
}
