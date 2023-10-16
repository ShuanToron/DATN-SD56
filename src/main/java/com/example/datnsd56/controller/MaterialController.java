package com.example.datnsd56.controller;

import com.example.datnsd56.model.Material;
import com.example.datnsd56.service.MaterialService;
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
@RequestMapping("/api/material")
public class MaterialController {
    @Autowired
    private MaterialService materialService;

    @GetMapping("getAll")
    public List<Material> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<Material> list = materialService.getAll(page).getContent();
        return list;
    }

    @PostMapping("add")
    public void add(@RequestBody Material material) {
        materialService.add(material);
    }

    @GetMapping("detail/{id}")
    public Material detail(@PathVariable("id") Integer id) {
        Material material = materialService.getById(id);
        return material;

    }

    @PutMapping("update/{id}")
    public void update(@RequestBody Material material) {
        materialService.update(material);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        materialService.delete(id);
    }
}
