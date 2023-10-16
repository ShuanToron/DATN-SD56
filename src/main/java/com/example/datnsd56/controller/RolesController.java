package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Roles;
import com.example.datnsd56.service.RolesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RolesController {
    @Autowired
    private RolesService rolesService;

    @GetMapping("getAll")
    public List<Roles> getAll(@RequestParam(value = "page",defaultValue = "0")Integer page){
        List<Roles> list=rolesService.getAll(page).getContent();
        return list;
    }
    @GetMapping("detail/{id}")
    public Roles detail(@PathVariable("id") Integer id){
       Roles roles= rolesService.detail(id);
        return roles;
    }
    @PostMapping("add")
    public void add(@RequestBody Roles roles){
        rolesService.add(roles);
    }
    @PutMapping("update/{id}")
    public void update(@RequestBody Roles roles){
        rolesService.update(roles);
    }
@DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id){
        rolesService.delete(id);
}
}
