package com.example.datnsd56.controller;

import com.example.datnsd56.model.Brand;
import com.example.datnsd56.service.BrandService;
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
@RequestMapping("/api/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;

    @GetMapping("getAll")
    public List<Brand> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<Brand> list = brandService.getAll(page).getContent();
        return list;
    }

    @PostMapping("add")
    public void add(@RequestBody Brand brand) {
        brandService.add(brand);
    }

    @GetMapping("detail/{id}")
    public Brand detail(@PathVariable("id") Integer id) {
        Brand brand = brandService.getById(id);
        return brand;

    }

    @PutMapping("update/{id}")
    public void update(@RequestBody Brand brand) {
        brandService.update(brand);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        brandService.delete(id);
    }
}
