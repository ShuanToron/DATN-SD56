package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Address;
import com.example.datnsd56.entity.Account;
import com.example.datnsd56.entity.Customers;
import com.example.datnsd56.entity.Roles;
import com.example.datnsd56.repository.AddressRepository;
import com.example.datnsd56.service.AddressService;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/address")
public class AddressController {
    @Autowired
    private AddressService addressService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private RolesService rolesService;

    //    @GetMapping("/hien-thi")
//    public String get(Model model){
//        model.addAttribute("address",new Address());
//        Page<Address> page = addressService.getAll(0);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("list", page);
//        model.addAttribute("currentPage", 0);
//        return "/dashboard/address/address";
//    }
    @GetMapping("/hien-thi")
    public String getAllBypage( Model model,@RequestParam(defaultValue = "0") Integer page){
        model.addAttribute("address",new Address());
        Page<Address> page1 = addressService.getAll(PageRequest.of(page,5));
//        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("list", page1);
        List<Account> listr=accountService.get();
        model.addAttribute("accountlist",listr);
        model.addAttribute("account",new Account());
        List<Roles> listr1=rolesService.getAll();
        model.addAttribute("rolelist",listr1);
        model.addAttribute("roles",new Roles());
        model.addAttribute("gender", 1); // hoặc có thể truyền giá trị từ đối tượng bạn sử dụng trong model

//        model.addAttribute("currentPage", pageNo);
        return "/dashboard/address/address";

    }
    @GetMapping("/view-update/{id}")
    public String detail(@PathVariable("id") Integer id,Model model){
//        model.addAttribute("address",new Address());
        Address address= addressService.detail(id);
        model.addAttribute("address",address);
        List<Account> listr=accountService.get();
        model.addAttribute("accountlist",listr);
        model.addAttribute("account",new Account());
        return "/dashboard/address/update";
    }
    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("address") Address address, BindingResult result, Model model, HttpSession session){
        if(result.hasErrors()){
            model.addAttribute("list",addressService.getAll(Pageable.unpaged()));
            List<Account> listr=accountService.get();
            model.addAttribute("accountlist",listr);
            model.addAttribute("account",new Account());
            return "/dashboard/address/address";

        }
        addressService.add(address);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/address/hien-thi";

    }
    @PostMapping("/update/{id}")
    public String update( @Valid @ModelAttribute("address") Address address, BindingResult result,@PathVariable("id") Integer id , Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("address",address);
            List<Account> listr=accountService.get();
            model.addAttribute("accountlist",listr);
            model.addAttribute("account",new Account());
            return "/dashboard/address/update";

        }
        addressService.update(address);
        session.setAttribute("successMessage", "sửa thành công");
        return "redirect:/admin/address/hien-thi";
    }
    @GetMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        addressService.delete(id);
        return "redirect:/admin/address/hien-thi";
    }
    @GetMapping("search")
    public String search(@RequestParam("phone") String phone, Model model, RedirectAttributes redirectAttributes) {
        Page<Address> addresss = addressService.findByEmail(phone);
        model.addAttribute("list", addresss);
//        model.addAttribute("list",addressService.getAll(Pageable.unpaged()));
        model.addAttribute("address",new Address() );
        model.addAttribute("account",new Account());
        List<Account> listr=accountService.get();
        model.addAttribute("accountlist",listr);
//        redirectAttributes.addAttribute("phone", phone);
        return "/dashboard/address/address";
    }

}
