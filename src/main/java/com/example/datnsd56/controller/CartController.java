package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Cart;
import com.example.datnsd56.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @GetMapping("getAll")
    public List<Cart> getAll(@RequestParam(value = "page",defaultValue = "0")Integer page){
        List<Cart> list=cartService.getAll(page).getContent();
        return list;
    }
    @GetMapping("detail/{id}")
    public Cart detail(@PathVariable("id") Integer id){
       Cart cart= cartService.detail(id);
        return cart;
    }
    @PostMapping("add")
    public void add(@RequestBody Cart cart){
        cartService.add(cart);
    }
    @PutMapping("update/{id}")
    public void update(@RequestBody Cart cart){
        cartService.update(cart);
    }
@DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id){
        cartService.delete(id);
}
}
