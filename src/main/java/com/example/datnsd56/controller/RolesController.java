package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Roles;
import com.example.datnsd56.service.RolesService;
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

import java.util.List;

@Controller
@RequestMapping("/admin/roles")
public class RolesController {
    @Autowired
    private RolesService rolesService;

        @GetMapping("/hien-thi1")
    public String get(Model model){
//        model.addAttribute("roles",new Roles());
        List<Roles> page = rolesService.getAll();
        model.addAttribute("list", page);
        return "/dashboard/roles/roles";
    }
    @GetMapping("/hien-thi")
    public String getAll( Model model,@RequestParam(defaultValue = "0") Integer page){
        model.addAttribute("roles",new Roles());
        Page<Roles> page1 = rolesService.getAllbypage(PageRequest.of(page,5));
//        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("list", page1);
//        model.addAttribute("currentPage", pageNo);
        return "/dashboard/roles/roles";

    }
    @GetMapping("/view-update/{id}")
    public String detail(@PathVariable("id") Integer id,Model model){
//        model.addAttribute("roles",new Roles());
        Roles roles= rolesService.detail(id);
        model.addAttribute("roles",roles);

        return "dashboard/roles/update-roles";
    }
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("roles") Roles roles, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            model.addAttribute("list",rolesService.getAllbypage(Pageable.unpaged()));
            return "/dashboard/roles/roles";

        }
        rolesService.add(roles);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/roles/hien-thi";

    }
    @PostMapping("/update/{id}")
    public String update( @Valid @ModelAttribute("roles") Roles roles, BindingResult result,@PathVariable("id") Integer id , Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("roles",roles);
            return "dashboard/roles/update-roles";

        }
        rolesService.update(roles);
        session.setAttribute("successMessage", "sửa thành công");
        return "redirect:/admin/roles/hien-thi";
    }
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        rolesService.delete(id);
        return "redirect:/admin/roles/hien-thi";
    }
}
