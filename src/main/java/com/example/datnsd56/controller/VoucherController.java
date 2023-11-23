package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Customers;
import com.example.datnsd56.entity.Voucher;
import com.example.datnsd56.service.VoucherService;
import com.example.datnsd56.service.impl.VoucherSeviceImpl;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/voucher")
public class VoucherController {
    @Autowired
    private VoucherSeviceImpl voucherService;

    @GetMapping
    public String getAllVouchers(Model model) {
        voucherService.checkAndDeactivateExpiredVouchers();
        model.addAttribute("voucher",new Voucher());
// Kiểm tra và cập nhật trạng thái trước khi hiển thị danh sách
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);

        return "dashboard/voucher/voucher";
    }
    @PostMapping("/new")
    public String newVoucherSubmit(@Valid @ModelAttribute("voucher") Voucher voucher, BindingResult bindingResult, RedirectAttributes redirectAttributes, Model model) {
        if (bindingResult.hasErrors()) {
            // Nếu có lỗi validation, điều hướng trở lại form với thông báo lỗi
            List<Voucher> vouchers = voucherService.getAllVouchers();
            model.addAttribute("vouchers", vouchers);
            return "/dashboard/voucher/voucher";
        }

        voucher.setActive(true);
        voucherService.saveVoucher(voucher);
        redirectAttributes.addFlashAttribute("successMessage", "Voucher created successfully!");


        return "redirect:/admin/voucher";
    }
    @GetMapping("/{id}")
    public String getVoucherById(@PathVariable Integer id, Model model) {
        Voucher voucher = voucherService.getVoucherById(id);
        model.addAttribute("voucher", voucher);
        return "dashboard/voucher/update-voucher";
    }

//    @GetMapping("/new")
//    public String newVoucherForm(Model model) {
//        model.addAttribute("voucher", new Voucher());
//        return "dashboard/voucher/new";
//    }

//    @PostMapping("/new")
//    public String newVoucherSubmit(@ModelAttribute Voucher voucher, RedirectAttributes redirectAttributes) {
//        voucher.setActive(true);
//        voucherService.saveVoucher(voucher);
//        redirectAttributes.addFlashAttribute("successMessage", "Voucher created successfully!");
//        return "redirect:/admin/voucher";
//    }

    @GetMapping("/delete/{id}")
    public String deleteVoucher(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        voucherService.deleteVoucher(id);
        redirectAttributes.addFlashAttribute("successMessage", "Voucher deleted successfully!");
        return "redirect:/admin/voucher";
    }
}
