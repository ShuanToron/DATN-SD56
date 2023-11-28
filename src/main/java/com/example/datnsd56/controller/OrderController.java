//package com.example.datnsd56.controller;
//
//import com.example.datnsd56.entity.Account;
//import com.example.datnsd56.entity.Address;
//import com.example.datnsd56.entity.Cart;
//import com.example.datnsd56.service.AccountService;
//import com.example.datnsd56.service.AddressService;
//import com.example.datnsd56.service.OrdersService;
//import com.fasterxml.jackson.databind.util.JSONPObject;
//import lombok.Getter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.security.Principal;
//import java.util.List;
//
//@Controller
//public class OrderController {
//    @Autowired
//    private AccountService accountService;
//    @Autowired
//    private OrdersService ordersService;
//    @Autowired
//    private AddressService service;
//
//    @GetMapping("/checkout")
//    public String checkout(Principal principal, Model model){
//        if(principal == null){
//            return "redirect:/login";
//        }
//        Account account = accountService.findByEmail(principal.getName());
//        List<Cart> cart = account.getCarts();
//        Address defAddress = service.findAccountDefaultAddress(account.getId());
//        model.addAttribute("cart",cart);
//        model.addAttribute("defAddress",defAddress);
//        return "website/index/giohang";
//
//    }
//
//}
