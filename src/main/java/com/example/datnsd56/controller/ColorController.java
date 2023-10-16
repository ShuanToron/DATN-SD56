package com.example.datnsd56.controller;

import com.example.datnsd56.model.Color;
import com.example.datnsd56.service.ColorService;
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
@RequestMapping("/api/color")
public class ColorController {
    @Autowired
    private ColorService colorService;

    @GetMapping("getAll")
    public List<Color> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<Color> list = colorService.getAll(page).getContent();
        return list;
    }

    @PostMapping("add")
    public void add(@RequestBody Color color) {
        colorService.add(color);
    }

    @GetMapping("detail/{id}")
    public Color detail(@PathVariable("id") Integer id) {
        Color color = colorService.getById(id);
        return color;

    }

    @PutMapping("update/{id}")
    public void update(@RequestBody Color color) {
        colorService.update(color);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        colorService.delete(id);
    }

}
