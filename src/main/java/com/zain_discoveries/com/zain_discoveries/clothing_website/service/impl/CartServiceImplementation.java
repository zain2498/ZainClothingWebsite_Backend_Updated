package com.zain_discoveries.com.zain_discoveries.clothing_website.service.impl;

import com.zain_discoveries.com.zain_discoveries.clothing_website.exception.ProductException;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Cart;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.CartItem;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.Product;
import com.zain_discoveries.com.zain_discoveries.clothing_website.model.User;
import com.zain_discoveries.com.zain_discoveries.clothing_website.repository.CartRepository;
import com.zain_discoveries.com.zain_discoveries.clothing_website.request.AddItemRequest;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.CartItemService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.CartService;
import com.zain_discoveries.com.zain_discoveries.clothing_website.service.ProductService;
import org.springframework.stereotype.Service;

@Service
public class CartServiceImplementation implements CartService {

    private CartRepository cartRepository;
    private CartItemService cartItemService;
    private ProductService productService;

    public CartServiceImplementation(CartRepository cartRepository, ProductService productService, CartItemService cartItemService){
        this.cartRepository = cartRepository;
        this.cartItemService = cartItemService;
        this.productService = productService;
    }
    @Override
    public Cart createCart(User user) {
        Cart cart = new Cart();
        cart.setUser(user);
        return cartRepository.save(cart);
    }

    @Override
    public String addToCart(Long userId, AddItemRequest request) throws ProductException {
        Cart cart = cartRepository.findCartByUserId(userId);
        Product product = productService.findProductById(request.getProductId());
        CartItem cartItemExist = cartItemService.isCartItemExist(cart,product,request.getSize(),userId);

        if (cartItemExist != null){
            CartItem cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setUserId(userId);
            cartItem.setQuantity(request.getQuantity());
            cartItem.setPrice(request.getPrice()* request.getQuantity());
            cartItem.setSize(request.getSize());

            CartItem createdCartItem = cartItemService.createCartItem(cartItem);
            cart.getCartItems().add(createdCartItem);
        }
        return "Items are added to the cart";
    }

    @Override
    public Cart findUserCart(Long userId) throws Exception {
        return null;
    }
}
