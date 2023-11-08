package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Size;
import com.example.datnsd56.service.SizeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/admin/kich-co")
public class SizeController {
    @Qualifier("sizeServiceImpl")
    @Autowired
    private SizeService service;

    @GetMapping("/hien-thi1")
    public String get(Model model){
//        model.addAttribute("roles",new Roles());
        List<Size> page = service.getAllSZ();
        model.addAttribute("list", page);
        return "/dashboard/kich-co/kich-co";
    }
    @GetMapping("/hien-thi")
    public String viewChatLieu(@RequestParam(value = "page", defaultValue = "0") Integer pageNo, Model model) {
        model.addAttribute("size", new Size());
        Page<Size> page = service.getAll(pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("list", page);
        model.addAttribute("currentPage", pageNo);
        return "/dashboard/kich-co/kich-co";
    }

    @GetMapping("/view-update/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Size size= service.getById(id);
        model.addAttribute("size", size);
        return "/dashboard/kich-co/update-kich-co";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid @ModelAttribute("size") Size size, BindingResult result, @PathVariable("id") Integer id, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("size", size);
            return "/dashboard/kich-co/update-kich-co";

        }
        service.update(size);
        session.setAttribute("successMessage", "sửa thành công");
        return "redirect:/admin/kich-co/hien-thi";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return "redirect:/admin/kich-co/hien-thi";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("size") Size size, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            Page<Size> page = service.getAll(0);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("list", page);
            model.addAttribute("currentPage", 0);
            return "/dashboard/kich-co/kich-co";
        }
        String code = "KC" + new Random().nextInt(100000);
        size.setCode(code);
        size.setStatus(true);
        model.addAttribute("size", size);
        service.add(size);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/kich-co/hien-thi";

    }
    @PostMapping("/add1")
    public String add1(@Valid @ModelAttribute("size") Size size, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            Page<Size> page = service.getAll(0);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("list", page);
            model.addAttribute("currentPage", 0);
            return "/dashboard/kich-co/kich-co";
        }
        String code = "SZ" + new Random().nextInt(100000);
        size.setCode(code);
        size.setStatus(true);
        model.addAttribute("size", size);
        service.add(size);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/chi-tiet-san-pham/hien-thi";

    }
    @PostMapping("/add2")
    public String add2(@Valid @ModelAttribute("size") Size size, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            Page<Size> page = service.getAll(0);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("list", page);
            model.addAttribute("currentPage", 0);
            return "/dashboard/kich-co/kich-co";
        }
        String code = "SZ" + new Random().nextInt(100000);
        size.setCode(code);
        size.setStatus(true);
        model.addAttribute("size", size);
        service.add(size);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/san-pham-test/create";

    }



}
