package com.example.datnsd56.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ViewProductController {
    @GetMapping("/trang-chu")
    public String hienthi(){
        return "/website/index/index";
    }
    @GetMapping("/hien-thi")
    public String productView(){
        return "/website/index/product";
    }
}
