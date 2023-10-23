package com.example.datnsd56.controller;

import com.example.datnsd56.entity.ShoeSole;
import com.example.datnsd56.entity.Size;
import com.example.datnsd56.service.SizeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/kich-thuoc/")
public class SizeController {
    @Autowired
    private SizeService sizeService;


    @GetMapping("hien-thi")
    public String viewSize(@RequestParam(value = "page", defaultValue = "0") Integer pageNo, Model model) {
        model.addAttribute("size", new Size());
        Page<Size> page = sizeService.getAll(pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("list", page);
        model.addAttribute("currentPage", pageNo);
        return "/dashboard/kich-thuoc/kich-thuoc";
    }

    @GetMapping("view-update/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Size size = sizeService.getById(id);
        model.addAttribute("size", size);
        return "/dashboard/kich-thuoc/update-kich-thuoc";
    }

    @PostMapping("update/{id}")
    public String update(@Valid @ModelAttribute("size") Size size, BindingResult result, @PathVariable("id") Integer id, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("size", size);
            return "/dashboard/kich-thuoc/update-kich-thuoc";

        }
        sizeService.update(size);
        session.setAttribute("successMessage", "sửa thành công");
        return "redirect:/admin/kich-thuoc/hien-thi";
    }

    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        sizeService.delete(id);
        return "redirect:/admin/kich-thuoc/hien-thi";
    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("size") Size size, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            Page<Size> page = sizeService.getAll(0);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("list", page);
            model.addAttribute("currentPage", 0);
            return "/dashboard/kich-thuoc/loai-giay";
        }
        sizeService.add(size);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/kich-thuoc/hien-thi";

    }
}
