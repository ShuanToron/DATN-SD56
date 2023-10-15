package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Voucher;
import com.example.datnsd56.service.VoucherService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/voucher")
public class VoucherController {
    @Autowired
    private VoucherService voucherService;

//    @GetMapping("/hien-thi")
//    public String get(Model model){
//        model.addAttribute("voucher",new Voucher());
//        Page<Voucher> page = voucherService.getAll(0);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("list", page);
//        model.addAttribute("currentPage", 0);
//        return "/dashboard/voucher/voucher";
//    }
    @GetMapping("/hien-thi")
    public String getAllBypage( Model model,@RequestParam(defaultValue = "0") Integer page){
        model.addAttribute("voucher",new Voucher());
        Page<Voucher> page1 = voucherService.getAllbypad(PageRequest.of(page,5));
//        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("list", page1);
//        model.addAttribute("currentPage", pageNo);
        return "/dashboard/voucher/voucher";

    }
    @GetMapping("/view-update/{id}")
    public String detail(@PathVariable("id") Integer id,Model model){
//        model.addAttribute("voucher",new Voucher());
       Voucher voucher= voucherService.detail(id);
        model.addAttribute("voucher",voucher);

        return "dashboard/voucher/update-voucher";
    }
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("voucher") Voucher voucher, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            model.addAttribute("list",voucherService.get());
            return "/dashboard/voucher/voucher";

        }
        voucherService.add(voucher);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/voucher/hien-thi";

    }
    @PostMapping("/update/{id}")
    public String update( @Valid @ModelAttribute("voucher") Voucher voucher, BindingResult result,@PathVariable("id") Integer id , Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("voucher",voucher);
            return "dashboard/voucher/update-voucher";

        }
        voucherService.update(voucher);
        session.setAttribute("successMessage", "sửa thành công");
        return "redirect:/admin/voucher/hien-thi";
    }
@GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        voucherService.delete(id);
    return "redirect:/admin/voucher/hien-thi";
}
}
