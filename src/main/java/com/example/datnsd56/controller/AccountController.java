package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Account;
import com.example.datnsd56.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @GetMapping("getAll")
    public List<Account> getAll(@RequestParam(value = "page",defaultValue = "0")Integer page){
        List<Account> list=accountService.getAll(page).getContent();
        return list;
    }
    @GetMapping("detail/{id}")
    public Account detail(@PathVariable("id") Integer id){
       Account account= accountService.detail(id);
        return account;
    }
    @PostMapping("add")
    public void add(@RequestBody Account account){
        accountService.add(account);
    }
    @PutMapping("update/{id}")
    public void update(@RequestBody Account account){
        accountService.update(account);
    }
@DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id){
        accountService.delete(id);
}
}
