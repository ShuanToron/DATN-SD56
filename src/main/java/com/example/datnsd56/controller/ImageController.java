package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Image;
import com.example.datnsd56.service.ImageService;
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
@RequestMapping("/api/image")
public class ImageController {
    @Autowired
    private ImageService imageService;

    @GetMapping("getAll")
    public List<Image> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<Image> list = imageService.getAll(page).getContent();
        return list;
    }

    @PostMapping("add")
    public void add(@RequestBody Image image) {
        imageService.add(image);
    }

    @GetMapping("detail/{id}")
    public Image detail(@PathVariable("id") Integer id) {
        Image image = imageService.getById(id);
        return image;

    }

    @PutMapping("update/{id}")
    public void update(@RequestBody Image image) {
        imageService.update(image);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        imageService.delete(id);
    }
}
