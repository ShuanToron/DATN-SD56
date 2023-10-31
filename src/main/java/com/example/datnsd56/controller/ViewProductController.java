package com.example.datnsd56.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view-product")
public class ViewProductController {
    @GetMapping("/hien-thi")
    public String hienthi(){
        return "/dashboard/index/index";
    }
}
