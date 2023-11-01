package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Brand;
import com.example.datnsd56.entity.Category;
import com.example.datnsd56.entity.Image;
import com.example.datnsd56.entity.Material;
import com.example.datnsd56.entity.Products;
import com.example.datnsd56.entity.ShoeSole;
import com.example.datnsd56.service.BrandService;
import com.example.datnsd56.service.CategoryService;
import com.example.datnsd56.service.ImageService;
import com.example.datnsd56.service.MaterialService;
import com.example.datnsd56.service.ProductsService;
import com.example.datnsd56.service.ShoeSoleService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/san-pham")
public class ProductsController {
    @Qualifier("productsServiceImpl")
    @Autowired
    private ProductsService service;
    @Autowired
    private BrandService brand;
    @Autowired
    private CategoryService category;
    @Autowired
    private MaterialService materialService;
    @Autowired
    private ShoeSoleService shoeSole;


    @GetMapping("/hien-thi")
    public String viewChatLieu(@RequestParam(value = "page", defaultValue = "0") Integer pageNo, Model model, HttpServletResponse response) {
        model.addAttribute("product", new Products());
        Page<Products> page = service.getAll(pageNo);
        List<Brand> brands = brand.getAllBrand();
        model.addAttribute("brand", brands);
        List<Category> categories = category.getAllCate();
        model.addAttribute("category", categories);
        List<Material> materials = materialService.getAllMater();
        model.addAttribute("material", materials);
        List<ShoeSole> shoeSoles = shoeSole.getAllSole();
        model.addAttribute("shoeSole", shoeSoles);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("list", page);
        model.addAttribute("currentPage", pageNo);
        return "dashboard/san-pham/san-pham";
    }

    @GetMapping("/view-update/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Products products = service.getById(id);
        model.addAttribute("product", products);
        List<Brand> brands = brand.getAllBrand();
        model.addAttribute("brand", brands);
        List<Category> categories = category.getAllCate();
        model.addAttribute("category", categories);

        List<Material> materials = materialService.getAllMater();
        model.addAttribute("material", materials);
        List<ShoeSole> shoeSoles = shoeSole.getAllSole();
        model.addAttribute("shoeSole", shoeSoles);

        return "/dashboard/san-pham/update-san-pham";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid @ModelAttribute("product") Products products, BindingResult
            result, @PathVariable("id") Integer id, Model model, HttpSession session) {
        if (result.hasErrors()) {
            model.addAttribute("product", products);
            List<Brand> brands = brand.getAllBrand();
            model.addAttribute("brand", brands);
            List<Category> categories = category.getAllCate();


            List<Material> materials = materialService.getAllMater();
            model.addAttribute("material", materials);
            List<ShoeSole> shoeSoles = shoeSole.getAllSole();

            return "/dashboard/san-pham/update-san-pham";

        }
        service.update(products);
        session.setAttribute("successMessage", "sửa thành công");
        return "redirect:/admin/san-pham/hien-thi";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        service.delete(id);
        return "redirect:/admin/san-pham/hien-thi";
    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("product") Products products, BindingResult result, Model
            model, HttpSession session) {
        if (result.hasErrors()) {
            Page<Products> page = service.getAll(0);
            List<Brand> brands = brand.getAllBrand();
            model.addAttribute("brand", brands);
            List<Category> categories = category.getAllCate();
            model.addAttribute("category", categories);

            List<Material> materials = materialService.getAllMater();
            model.addAttribute("material", materials);
            List<ShoeSole> shoeSoles = shoeSole.getAllSole();
            model.addAttribute("shoeSole", shoeSoles);

            model.addAttribute("totalPages", page.getTotalPages());
            model.addAttribute("list", page);
            model.addAttribute("currentPage", 0);
            return "dashboard/san-pham/san-pham";
        }
        service.add(products);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/san-pham/hien-thi";

    }

}
