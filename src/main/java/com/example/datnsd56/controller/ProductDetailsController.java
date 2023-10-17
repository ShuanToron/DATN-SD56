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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api/productDetails/")
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
    public String getAllBypage(Model model, @RequestParam(defaultValue = "0",name = "page") Integer page){
        Pageable pageable = PageRequest.of(page,5);
        Page<ProductDetails> productDetails= productDetailsService.getAll(pageable);
        List<Products> products = productsService.getAll(page).getContent();
        List<Color> colors = colorService.getAll(page).getContent();
        List<Size> sizes =  sizeService.getAll(page).getContent();
//        model.addAttribute("totalPages", page1.getTotalPages());
        model.addAttribute("productDetails", productDetails);
        model.addAttribute("products", products);
        model.addAttribute("colors", colors);
        model.addAttribute("sizes", sizes);
        model.addAttribute("ctsp",new ProductDetails());

//        model.addAttribute("currentPage", pageNo);
        return "/dashboard/chi-tiet-san-pham/chi-tiet-san-pham";

    }

    @PostMapping("/add")
    public String add(@Valid @ModelAttribute("ctsp") ProductDetails productDetails, BindingResult result,@RequestParam(defaultValue = "0",name = "page") Integer page, Model model, HttpSession session){
        if(result.hasErrors()){
            Pageable pageable = PageRequest.of(page,5);
            Page<ProductDetails> productDetail= productDetailsService.getAll(pageable);
            List<Products> products = productsService.getAll(page).getContent();
            List<Color> colors = colorService.getAll(page).getContent();
            List<Size> sizes =  sizeService.getAll(page).getContent();
            model.addAttribute("productDetail", productDetail);
            model.addAttribute("products", products);
            model.addAttribute("colors", colors);
            model.addAttribute("sizes", sizes);
            return "/dashboard/chi-tiet-san-pham/chi-tiet-san-pham";

        }
        productDetailsService.add(productDetails);
        session.setAttribute("successMessage", "Thêm thành công");
        return "redirect:/api/productDetails/hien-thi";

    }
}
