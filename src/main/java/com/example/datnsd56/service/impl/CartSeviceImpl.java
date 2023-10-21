package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Cart;
import com.example.datnsd56.repository.CartRepository;
import com.example.datnsd56.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CartSeviceImpl implements CartService {
    @Autowired
    private CartRepository cartRepository;

    @Override
    public Page<Cart> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return cartRepository.findAll(pageable);
    }

    @Override
    public Cart detail(Integer id) {
        Cart cart = cartRepository.findById(id).get();
        return cart;
    }

    @Override
    public void add(Cart cart) {
        cartRepository.save(cart);
    }

    @Override
    public void update(Cart cart) {
        cartRepository.save(cart);

    }

    @Override
    public void delete(Integer id) {
        Cart cart = detail(id);
        cartRepository.delete(cart);
    }
}
