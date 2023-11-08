package com.example.datnsd56.service;

import com.example.datnsd56.ViewModel.ViewCart;
import com.example.datnsd56.entity.Cart;
import com.example.datnsd56.entity.CartItem;
import com.example.datnsd56.entity.ProductDetails;
import org.springframework.data.domain.Page;

import java.util.Collection;
import java.util.List;

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
