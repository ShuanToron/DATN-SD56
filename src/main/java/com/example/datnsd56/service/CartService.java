package com.example.datnsd56.service;

import com.example.datnsd56.entity.Cart;
import org.springframework.data.domain.Page;

public interface CartService {
    Page<Cart> getAll(Integer page);
    Cart detail(Integer id);
    void add(Cart cart);
    void update(Cart cart);
    void delete(Integer id);

}
