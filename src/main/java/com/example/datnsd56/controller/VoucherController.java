package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Voucher;
import com.example.datnsd56.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/voucher")
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

    @GetMapping("getAll")
    public List<Voucher> getAll(@RequestParam(value = "page",defaultValue = "0")Integer page){
        List<Voucher> list=voucherService.getAll(page).getContent();
        return list;
    }
    @GetMapping("detail/{id}")
    public Voucher detail(@PathVariable("id") Integer id){
       Voucher voucher= voucherService.detail(id);
        return voucher;
    }
    @PostMapping("add")
    public void add(@RequestBody Voucher voucher){
        voucherService.add(voucher);
    }
    @PutMapping("update/{id}")
    public void update(@RequestBody Voucher voucher){
        voucherService.update(voucher);
    }
@DeleteMapping("delete/{id}")
    public void delete(@PathVariable("id") Integer id){
        voucherService.delete(id);
}
}
