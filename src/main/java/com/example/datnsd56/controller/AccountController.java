package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Account;
import com.example.datnsd56.entity.Roles;
import com.example.datnsd56.service.AccountService;
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
@RequestMapping("/admin/account")
public class AccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private RolesService rolesService;

    //    @GetMapping("/hien-thi")
//    public String get(Model model){
//        model.addAttribute("account",new Account());
//        Page<Account> page = accountService.getAll(0);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("list", page);
//        model.addAttribute("currentPage", 0);
//        return "/dashboard/account/account";
//    }
    @GetMapping("/hien-thi")
    public String getAllBypage( Model model,@RequestParam(defaultValue = "0") Integer page){
        model.addAttribute("account",new Account());
        Page<Account> page1 = accountService.getAll(PageRequest.of(page,5));
//        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("list", page1);
        List<Roles> listr=rolesService.getAll();
        model.addAttribute("rolelist",listr);
        model.addAttribute("roles",new Roles());

//        model.addAttribute("currentPage", pageNo);
        return "/dashboard/account/account";

    }
    @GetMapping("/view-update/{id}")
    public String detail(@PathVariable("id") Integer id,Model model){
//        model.addAttribute("account",new Account());
        Account account= accountService.detail(id);
        model.addAttribute("account",account);
        List<Roles> listr=rolesService.getAll();
        model.addAttribute("rolelist",listr);
        model.addAttribute("roles",new Roles());
        return "dashboard/account/update-account";
    }
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("account") Account account, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            model.addAttribute("list",accountService.getAll(Pageable.unpaged()));
            List<Roles> listr=rolesService.getAll();
            model.addAttribute("rolelist",listr);
            model.addAttribute("roles",new Roles());
            return "/dashboard/account/account";

        }
        accountService.add(account);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/account/hien-thi";

    }
    @PostMapping("/update/{id}")
    public String update( @Valid @ModelAttribute("account") Account account, BindingResult result,@PathVariable("id") Integer id , Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("account",account);
            return "dashboard/account/update-account";

        }
        accountService.update(account);
        session.setAttribute("successMessage", "sửa thành công");
        return "redirect:/admin/account/hien-thi";
    }
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        accountService.delete(id);
        return "redirect:/admin/account/hien-thi";
    }
}
