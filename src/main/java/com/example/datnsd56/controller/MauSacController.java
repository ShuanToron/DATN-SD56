package com.example.datnsd56.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/mau-sac")
public class MauSacController {
    @GetMapping("/hien-thi")
    public String viewMauSac() {
        return "/dashboard/MauSac";
    }
}
