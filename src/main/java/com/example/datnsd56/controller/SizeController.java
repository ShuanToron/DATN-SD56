package com.example.datnsd56.controller;

import com.example.datnsd56.model.Size;
import com.example.datnsd56.service.SizeService;
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
@RequestMapping("/api/size")
public class SizeController {
    @Autowired
    private SizeService sizeService;

    @GetMapping("getAll")
    public List<Size> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<Size> list = sizeService.getAll(page).getContent();
        return list;
    }

    @PostMapping("add")
    public void add(@RequestBody Size size) {
        sizeService.add(size);
    }

    @GetMapping("detail/{id}")
    public Size detail(@PathVariable("id") Integer id) {
        Size size = sizeService.getById(id);
        return size;

    }

    @PutMapping("update/{id}")
    public void update(@RequestBody Size size) {
        sizeService.update(size);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        sizeService.delete(id);
    }
}
