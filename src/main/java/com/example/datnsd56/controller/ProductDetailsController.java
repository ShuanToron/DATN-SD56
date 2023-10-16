package com.example.datnsd56.controller;

import com.example.datnsd56.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/productDetails")
public class ProductDetailsController {
    @Autowired
    private ProductDetailsService productDetailsService;
}
