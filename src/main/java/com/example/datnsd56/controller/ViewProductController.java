package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Image;
import com.example.datnsd56.entity.ProductDetails;
import com.example.datnsd56.entity.Products;
import com.example.datnsd56.service.ImageService;
import com.example.datnsd56.service.ProductDetailsService;
import com.example.datnsd56.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping("/product")
public class ViewProductController {

    @Autowired
    private ProductDetailsService productDetailsService;
    @Autowired
    ProductsService productsService;
    @Autowired
    ImageService imageService;
    @GetMapping("/trang-chu")
    public String hienthi(){
        return "/website/index/index";
    }
    @GetMapping("/hien-thi")
    public String productView(Model model) {
//List<Products>lists=productsService.getAllPro();
        List<ProductDetails> list = productDetailsService.getAllCTSP();
        List<Products> lists = productsService.getAllPro();
        // Sắp xếp sản phẩm theo brand
        Collections.sort(list, Comparator.comparing(product -> product.getProductId().getBrandId().getName()));
        model.addAttribute("views", list);
// Tạo một Map để nhóm sản phẩm theo brand
        Map<String, List<ProductDetails>> productsByBrand = new HashMap<>();

        List<ProductDetails> productList = null;
        for (ProductDetails product : list) {
            String brandName = product.getProductId().getBrandId().getName();
            productList = productsByBrand.getOrDefault(brandName, new ArrayList<>());
            productList.add(product);
            productsByBrand.put(brandName, productList);
        }
        if (productsByBrand == null) {
            productsByBrand = new HashMap<>();
        }

        model.addAttribute("productsByBrand", productsByBrand);


//        List<Image> listIm=  imageService.getall();
//        model.addAttribute("view",list);
//        model.addAttribute("viewss",listIm);
        model.addAttribute("views", list);
        return "website/index/product";
    }
    @GetMapping("/display")
    public ResponseEntity<byte[]> getImage(@RequestParam("id") Integer productId) throws SQLException {
        List<Image> imageList= imageService.getImagesForProducts(productId);
        byte[] imageBytes = null;
        imageBytes = imageList.get(0).getUrl().getBytes(1, (int) imageList.get(0).getUrl().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
//        if (image != null && image.get(0) != null) {
//            return ResponseEntity.ok()
//                .contentType(MediaType.IMAGE_JPEG)
//                .body(image.get(0).getUrl());
//        } else {
//            return ResponseEntity.notFound().build();
//        }

    }
}
