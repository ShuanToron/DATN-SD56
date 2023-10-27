package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Color;
import com.example.datnsd56.entity.Roles;
import com.example.datnsd56.service.ColorService;
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

import java.util.List;
import java.util.Random;

@Controller
@RequestMapping("/admin/mau-sac")
public class ColorController {
    @Qualifier("colorServiceImpl")
    @Autowired
    private ColorService service;

    @GetMapping("/hien-thi1")
    public String get(Model model){
//        model.addAttribute("roles",new Roles());
        List<Color> page = service.getAllColor();
        model.addAttribute("list", page);
        return "/dashboard/mau-sac/mau-sac";
    }
    @GetMapping("/hien-thi")
    public String viewChatLieu(@RequestParam(value = "page", defaultValue = "0") Integer pageNo, Model model) {
        model.addAttribute("color", new Color());
        Page<Color> page = service.getAll(pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("list", page);
        model.addAttribute("currentPage", pageNo);
        return "/dashboard/mau-sac/mau-sac";
    }

    @GetMapping("/view-update/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Color color = service.getById(id);
        model.addAttribute("color", color);
        return "/dashboard/mau-sac/update-mau-sac";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid @ModelAttribute("color") Color color, BindingResult result, @PathVariable("id") Integer id, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("color", color);
            return "/dashboard/mau-sac/update-mau-sac";

        }
        service.update(color);
        session.setAttribute("successMessage", "sửa thành công");
        return "redirect:/admin/mau-sac/hien-thi";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return "redirect:/admin/mau-sac/hien-thi";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("color") Color color, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            Page<Color> page = service.getAll(0);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("list", page);
            model.addAttribute("currentPage", 0);
            return "/dashboard/mau-sac/mau-sac";
        }
        String code = "MS" + new Random().nextInt(100000);
        color.setCode(code);
        color.setStatus(true);
        model.addAttribute("color", color);
        service.add(color);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/mau-sac/hien-thi";

    }
    @PostMapping("/add1")
    public String add1(@Valid @ModelAttribute("color") Color color, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            Page<Color> page = service.getAll(0);
            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("list", page);
            model.addAttribute("currentPage", 0);
            return "/dashboard/mau-sac/mau-sac";
        }
        String code = "MS" + new Random().nextInt(100000);
        color.setCode(code);
        color.setStatus(true);
        model.addAttribute("color", color);
        service.add(color);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/chi-tiet-san-pham/hien-thi";

    }
}
