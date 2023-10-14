package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Material;
import com.example.datnsd56.service.ChatLieuService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequestMapping("/admin/chat-lieu")
public class ChatLieuController {
    @Qualifier("chatLieuServiceImpl")
    @Autowired
    private ChatLieuService service;

    @GetMapping("/hien-thi")
    public String viewChatLieu(Model model) {
        model.addAttribute("chatlieu", new Material());
        Page<Material> page = service.pageMaterial(0);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("list", page);
        model.addAttribute("currentPage", 0);
        return "/dashboard/chat-lieu/chat-lieu";
    }

    @GetMapping("/hien-thi/{page}")
    public String pageChatLieu(@PathVariable("page") Integer pageNo, Model model) {
        model.addAttribute("chatlieu", new Material());
        Page<Material> page = service.pageMaterial(pageNo);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("list", page);
        model.addAttribute("currentPage", pageNo);
        return "/dashboard/chat-lieu/chat-lieu";
    }

    @GetMapping("/update")
    public String viewChatLieu1(Model model) {
        model.addAttribute("chatlieu", new Material());
        return "/dashboard/chat-lieu/update-chat-lieu";
    }

    @PostMapping("/delete/{id}")
    public String deleteChatLieu(@PathVariable("id") Integer id, HttpSession session) {
        service.removeChatLieu(id);
        session.setAttribute("successMessage", "Xoá thành công");
        return "redirect:/admin/chat-lieu/hien-thi";
    }

    @PostMapping("/add")
    public String addChatLieu(@Valid @ModelAttribute("chatlieu") Material material, BindingResult result, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("list", service.getAllChatLieu());
            return "/dashboard/chat-lieu/chat-lieu";
        }
        service.addChatLieu(material);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/chat-lieu/hien-thi";
    }
}
