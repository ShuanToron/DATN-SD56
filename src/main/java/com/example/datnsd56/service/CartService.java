package com.example.datnsd56.service;

import com.example.datnsd56.entity.CartItem;

import java.util.Collection;

public interface CartService {
    void add(CartItem item);

    void remove(Integer id);

    void clear();

    int getCount();

    CartItem update(Integer id, Integer quantity);

    Collection<CartItem> getAllItem();
//    List<ViewCart> getAllCartItem();

    double getAmount();
//   void addToCart(CartItem cartItem);

}
