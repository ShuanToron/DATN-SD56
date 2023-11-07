package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Image;
import com.example.datnsd56.entity.ProductDetails;
import com.example.datnsd56.entity.Products;
import com.example.datnsd56.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.*;

@Controller
@RequestMapping("/product/detail")
public class DetailProductController {
    @Autowired
    private SizeService sizeService ;
    @Autowired
    private ColorService colorService;
    @Autowired
    private ProductDetailsService productDetailsService;
    @Autowired
    private ProductsService productsService;
    @Autowired
    private ImageService imageService;
    @GetMapping("chi-tiet/{id}")
    public String detail(@PathVariable("id") Integer id, Model model){
      Products list=  productDetailsService.getOneProdcut(id);
//         Tính toán giá sản phẩm dựa trên màu sắc và kích cỡ được chọn
        model.addAttribute("listSize",sizeService.getColorId(id));
        model.addAttribute("listColor",colorService.getColorId(id));
        model.addAttribute("sell",productDetailsService.getProductDetailsById(id));
//        model.addAttribute("sells",productDetailsService.findProductDetailsByColorIdAndSizeIdAndAndProductId(colorid,sizeId,id));
//        model.addAttribute("sell",productDetailsService.findProductDetailsBySellPrice(id));
        model.addAttribute("views",list);
        return "website/index/detail";


    }
    @GetMapping("/getProductPrice")
    public ResponseEntity<BigDecimal> getPrice(@RequestParam("productId") Integer id, @RequestParam("size") Integer size, @RequestParam("color") Integer color) {
        BigDecimal productDetailPrice = productDetailsService.getPrice(id, color, size);
        System.out.println(productDetailPrice);
        return ResponseEntity.ok().body(productDetailPrice);
    }
    @GetMapping("/display")
    public ResponseEntity<byte[]> getImage(@RequestParam("id") Integer productId) throws SQLException {
        List<Image> imageList= imageService.getImagesForProducts(productId);
        byte[] imageBytes = null;
        imageBytes = imageList.get(0).getUrl().getBytes(1, (int) imageList.get(0).getUrl().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);

    }
    @GetMapping("/detail")
    public String hienthi(){
        return "website/index/detail";
    }
}
