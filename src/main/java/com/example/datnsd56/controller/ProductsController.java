package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Products;
import com.example.datnsd56.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductsController {
    @Autowired
    private ProductsService productsService;

    @GetMapping("getAll")
    public List<Products> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<Products> list = productsService.getAll(page).getContent();
        return list;
    }

    @PostMapping("add")
    public void add(@RequestBody Products products) {
        productsService.add(products);
    }

    @GetMapping("detail/{id}")
    public Products detail(@PathVariable("id") Integer id) {
        Products products = productsService.getById(id);
        return products;

    }

    @PutMapping("update/{id}")
    public void update(@RequestBody Products products) {
        productsService.update(products);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        productsService.delete(id);
    }
}
