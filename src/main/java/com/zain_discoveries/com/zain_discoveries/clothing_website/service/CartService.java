package com.zain_discoveries.com.zain_discoveries.clothing_website.service;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.ProductException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Cart;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.request.AddItemRequest;

public interface CartService {
    public Cart createCart(User user);
    public String addToCart(Long userId, AddItemRequest request) throws ProductException;
    public Cart findUserCart(Long userId) throws Exception;
}
