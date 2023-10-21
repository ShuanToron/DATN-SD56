package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Customers;
import com.example.datnsd56.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {
    @Autowired
    private CustomersService customersService;

    @GetMapping("getAll")
    public List<Customers> getAll(@RequestParam(value = "page",defaultValue = "0")Integer page){
        List<Customers> list=customersService.getAll(page).getContent();
        return list;
    }
    @GetMapping("detail/{id}")
    public Customers detail(@PathVariable("id") Integer id){
       Customers customers= customersService.detail(id);
        return customers;
    }
    @PostMapping("add")
    public void add(@RequestBody Customers customers){
        customersService.add(customers);
    }
    @PutMapping("update/{id}")
    public void update(@RequestBody Customers customers){
        customersService.update(customers);
    }
@DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id){
        customersService.delete(id);
}
}
