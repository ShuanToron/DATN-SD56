package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Image;
import com.example.datnsd56.entity.ProductDetails;
import com.example.datnsd56.entity.Products;
import com.example.datnsd56.entity.Voucher;
import com.example.datnsd56.service.ImageService;
import com.example.datnsd56.service.ProductDetailsService;
import com.example.datnsd56.service.ProductsService;
import com.example.datnsd56.service.VoucherService;
import com.example.datnsd56.service.impl.VoucherSeviceImpl;
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
    @Autowired
    private VoucherSeviceImpl voucherService;
    @GetMapping("/trang-chu")
    public String hienthi(){
        return "/website/index/index";
    }
    @GetMapping("/hien-thi")
    public String productView(Model model) {
//List<Products>lists=productsService.getAllPro();
//        List<ProductDetails> list = productDetailsService.getAllCTSP();
        List<Products> lists = productsService.getAllPros();
        List<Voucher> vouchers = voucherService.getAllVouchers();
        model.addAttribute("vouchers", vouchers);
        // Sắp xếp sản phẩm theo brand
        Collections.sort(lists, Comparator.comparing(product -> product.getBrandId().getName()));
        model.addAttribute("views", lists);
// Tạo một Map để nhóm sản phẩm theo brand
        Map<String, List<Products>> productsByBrand = new HashMap<>();

        List<Products> productList = null;
        for (Products product : lists) {
            String brandName = product.getBrandId().getName();
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
//        model.addAttribute("views", list);
        return "website/index/product";
    }
    @GetMapping("/display")
    public ResponseEntity<byte[]> getImage(@RequestParam("id") Integer productId,@RequestParam("imageId") Integer imageId) throws SQLException {
        List<Image> imageList= imageService.getImagesForProducts(productId,imageId);
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

    @GetMapping("nike/hien-thi")
    public String viewNike(){
        return "website/index/nike";
    }
}
