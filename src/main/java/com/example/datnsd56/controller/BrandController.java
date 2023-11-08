package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Brand;
import com.example.datnsd56.service.BrandService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Random;

@Controller
@RequestMapping("/admin/thuong-hieu")
public class BrandController {
    @Qualifier("brandServiceImpl")
    @Autowired
    private BrandService service;

    @GetMapping("/hien-thi")
    public String viewChatLieu(@RequestParam(value = "page", defaultValue = "0") Integer pageNo, Model model) {
        model.addAttribute("brand", new Brand());
        Page<Brand> page = service.getAll(pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("list", page);
        model.addAttribute("currentPage", pageNo);
        return "/dashboard/thuong-hieu/thuong-hieu";
    }

    @GetMapping("/view-update/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Brand brand = service.getById(id);
        model.addAttribute("brand", brand);
        return "/dashboard/thuong-hieu/update-thuong-hieu";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid @ModelAttribute("brand") Brand brand, BindingResult result, @PathVariable("id") Integer id, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("brand", brand);
            return "/dashboard/thuong-hieu/update-thuong-hieu";

        }
        service.update(brand);
        session.setAttribute("successMessage", "sửa thành công");
        return "redirect:/admin/thuong-hieu/hien-thi";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return "redirect:/admin/thuong-hieu/hien-thi";
    }


    @PostMapping("/add1")
    public String add1(@Valid @ModelAttribute("brand") Brand brand, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            Page<Brand> page = service.getAll(0);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("list", page);
            model.addAttribute("currentPage", 0);
            return "/dashboard/thuong-hieu/thuong-hieu";        }
        String code = "DG" + new Random().nextInt(100000);
        brand.setCode(code);
        brand.setStatus(true);
        service.add(brand);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/san-pham-test/create";

    }
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("brand") Brand brand, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            Page<Brand> page = service.getAll(0);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("list", page);
            model.addAttribute("currentPage", 0);
            return "/dashboard/thuong-hieu/thuong-hieu";;
        }
        String code = "TH" + new Random().nextInt(100000);
        brand.setCode(code);
        brand.setStatus(true);
        service.add(brand);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/thuong-hieu/hien-thi";

    }
}
