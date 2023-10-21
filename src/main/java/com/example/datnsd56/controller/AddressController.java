package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Address;
import com.example.datnsd56.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/address")
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping("getAll")
    public List<Address> getAll(@RequestParam(value = "page",defaultValue = "0")Integer page){
        List<Address> list=addressService.getAll(page).getContent();
        return list;
    }
    @GetMapping("detail/{id}")
    public Address detail(@PathVariable("id") Integer id){
       Address address= addressService.detail(id);
        return address;
    }
    @PostMapping("add")
    public void add(@RequestBody Address address){
        addressService.add(address);
    }
    @PutMapping("update/{id}")
    public void update(@RequestBody Address address){
        addressService.update(address);
    }
@DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id){
        addressService.delete(id);
}
}
