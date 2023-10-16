package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Category;
import com.example.datnsd56.service.CategoryService;
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
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("getAll")
    public List<Category> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<Category> list = categoryService.getAll(page).getContent();
        return list;
    }

    @PostMapping("add")
    public void add(@RequestBody Category category) {
        categoryService.add(category);
    }

    @GetMapping("detail/{id}")
    public Category detail(@PathVariable("id") Integer id) {
        Category category = categoryService.getById(id);
        return category;

    }

    @PutMapping("update/{id}")
    public void update(@RequestBody Category category) {
        categoryService.update(category);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        categoryService.delete(id);
    }
}
