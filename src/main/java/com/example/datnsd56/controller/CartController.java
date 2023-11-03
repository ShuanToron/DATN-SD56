package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Cart;
import com.example.datnsd56.service.CartService;
import com.example.datnsd56.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductDetailsService productDetailsService;





}
