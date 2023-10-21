package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Color;
import com.example.datnsd56.entity.ProductDetails;
import com.example.datnsd56.entity.Products;
import com.example.datnsd56.entity.Size;
import com.example.datnsd56.service.ColorService;
import com.example.datnsd56.service.ProductDetailsService;
import com.example.datnsd56.service.ProductsService;
import com.example.datnsd56.service.SizeService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/chi-tiet-san-pham/")
public class ProductDetailsController {
    @Autowired
    private ProductDetailsService productDetailsService;

    @Autowired
    private ColorService colorService;

    @Autowired
    private SizeService sizeService;

    @Autowired
    private ProductsService productsService;

    @GetMapping("hien-thi")
    public String getAllBypage(Model model, @RequestParam(defaultValue = "0",name = "pageNo") Integer pagaNo){
        Page<ProductDetails> page= productDetailsService.getAll(pagaNo);
        List<Products> products = productsService.getAllSP();
        List<Color> colors = colorService.getAllCL();
        List<Size> sizes =  sizeService.getAllSZ();

        model.addAttribute("list", page);
        model.addAttribute("products", products);
        model.addAttribute("colors", colors);
        model.addAttribute("sizes", sizes);
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", pagaNo);
        model.addAttribute("ctsp",new ProductDetails());

//        model.addAttribute("currentPage", pageNo);
        return "/dashboard/chi-tiet-san-pham/chi-tiet-san-pham";

    }

    @PostMapping("add")
    public String add(@Valid @ModelAttribute("ctsp") ProductDetails productDetails, BindingResult result , Model model, HttpSession session){
        if(result.hasErrors()){
            Page<ProductDetails> productDetail= productDetailsService.getAll(0);
            List<Products> products = productsService.getAllSP();
            List<Color> colors = colorService.getAllCL();
            List<Size> sizes =  sizeService.getAllSZ();
            model.addAttribute("list", productDetail);
            model.addAttribute("products", products);
            model.addAttribute("colors", colors);
            model.addAttribute("sizes", sizes);
            model.addAttribute("totalPages", productDetail.getTotalPages());
            model.addAttribute("currentPage", 0);
            return "/dashboard/chi-tiet-san-pham/chi-tiet-san-pham";

        }
        productDetailsService.add(productDetails);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/admin/chi-tiet-san-pham/hien-thi";

    }

    @GetMapping("view-update/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        ProductDetails productDetails= productDetailsService.getById(id);
        model.addAttribute("ctsp", productDetails);
        List<Products> products = productsService.getAllSP();
        List<Color> colors = colorService.getAllCL();
        List<Size> sizes =  sizeService.getAllSZ();
        model.addAttribute("products", products);
        model.addAttribute("colors", colors);
        model.addAttribute("sizes", sizes);
        return "/dashboard/chi-tiet-san-pham/update-chi-tiet-san-pham";
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        productDetailsService.delete(id);
        return "redirect:/admin/chi-tiet-san-pham/hien-thi";
    }

    @PostMapping("/update/{id}")
    public String update(@Valid @ModelAttribute("ctsp") ProductDetails productDetails, BindingResult result, @PathVariable("id") Integer id, Model model, HttpSession session) {
        if (result.hasErrors()) {
            Page<ProductDetails> productDetail= productDetailsService.getAll(0);
            List<Products> products = productsService.getAllSP();
            List<Color> colors = colorService.getAllCL();
            List<Size> sizes =  sizeService.getAllSZ();
            model.addAttribute("productDetails", productDetail);
            model.addAttribute("products", products);
            model.addAttribute("colors", colors);
            model.addAttribute("sizes", sizes);
            return "/dashboard/chi-tiet-san-pham/update-chi-tiet-san-pham";

        }
        productDetailsService.update(productDetails);
        session.setAttribute("successMessage", "sửa thành công");
        return "redirect:/admin/chi-tiet-san-pham/hien-thi";
    }

    @GetMapping("search")
    public String search(
                         @RequestParam(value = "quantity", required = false) Integer quantity,
                         @RequestParam(value = "sellPrice", required = false) Integer sellPrice,
                         Model model, HttpSession session) {

        if (session.getAttribute("successMessage") != null) {
            String successMessage = (String) session.getAttribute("successMessage");
            model.addAttribute("successMessage", successMessage);
            session.removeAttribute("successMessage");
        }

        Page<ProductDetails> ketQuaTimKiem = productDetailsService.search(quantity, sellPrice, 5);
        List<Products> products = productsService.getAllSP();
        List<Color> colors = colorService.getAllCL();
        List<Size> sizes =  sizeService.getAllSZ();
        model.addAttribute("products", products);
        model.addAttribute("colors", colors);
        model.addAttribute("sizes", sizes);
        model.addAttribute("totalPages", ketQuaTimKiem.getTotalPages());
        model.addAttribute("currentPage", 0);
        model.addAttribute("productlist",new Products());
        model.addAttribute("colorlist",new Color());
        model.addAttribute("sizelist",new Size());
        model.addAttribute("list", ketQuaTimKiem);
        model.addAttribute("ctsp", new ProductDetails()); // Add this line to set the "att" attribute in the model

        // Add other model attributes if required

        return "/dashboard/chi-tiet-san-pham/chi-tiet-san-pham";
    }





}
