package com.example.datnsd56.controller;

import com.example.datnsd56.model.ShoeSole;
import com.example.datnsd56.service.ShoeSoleSevice;
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
@RequestMapping("/api/shoe_sole")
public class ShoeSoleController {
    @Autowired
    private ShoeSoleSevice shoeSoleSevice;

    @GetMapping("getAll")
    public List<ShoeSole> getAll(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<ShoeSole> list = shoeSoleSevice.getAll(page).getContent();
        return list;
    }

    @PostMapping("add")
    public void add(@RequestBody ShoeSole shoeSole) {
        shoeSoleSevice.add(shoeSole);
    }

    @GetMapping("detail/{id}")
    public ShoeSole detail(@PathVariable("id") Integer id) {
        ShoeSole shoeSole = shoeSoleSevice.getById(id);
        return shoeSole;

    }

    @PutMapping("update/{id}")
    public void update(@RequestBody ShoeSole shoeSole) {
        shoeSoleSevice.update(shoeSole);
    }

    @DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id) {
        shoeSoleSevice.delete(id);
    }
}
