package com.example.datnsd56.service;

import com.example.datnsd56.entity.Cart;
import com.example.datnsd56.entity.CartItem;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public interface CartService {
    void add(CartItem item);

    void remove(Integer id);

    void clear();

    int getCount();

    void add1(Cart cart);

    CartItem update(Integer id, Integer quantity);

    Collection<CartItem> getAllItem();
//    List<ViewCart> getAllCartItem();

    double getAmount();
//   void addToCart(CartItem cartItem);
 Cart findByNguoiDungId(Integer id);

}
