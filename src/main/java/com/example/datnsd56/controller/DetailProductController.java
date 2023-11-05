package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Image;
import com.example.datnsd56.entity.ProductDetails;
import com.example.datnsd56.entity.Products;
import com.example.datnsd56.service.ColorService;
import com.example.datnsd56.service.ImageService;
import com.example.datnsd56.service.ProductDetailsService;
import com.example.datnsd56.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private ImageService imageService;
    @GetMapping("chi-tiet/{id}")
    public String detail(@PathVariable("id") Integer id, Model model){
      Products list=  productDetailsService.getOneProdcut(id);
        model.addAttribute("listSize",sizeService.getColorId(id));
        model.addAttribute("listColor",colorService.getColorId(id));
        model.addAttribute("views",list);
        return "website/index/detail";


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
