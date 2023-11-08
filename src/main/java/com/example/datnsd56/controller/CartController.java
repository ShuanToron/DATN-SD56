package com.example.datnsd56.controller;

import com.example.datnsd56.entity.CartItem;
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

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
@Autowired
private CartService cartService;
    @Autowired
    private ImageService imageService;
    @Autowired
    private ColorService colorService;
    @Autowired
    private ProductDetailsService productDetailsService;
    @GetMapping("/view-cart")
    public String viewCart(   Model model) {

        model.addAttribute("cartItem",cartService.getAllItem());
        // Xử lý yêu cầu và thêm sản phẩm vào giỏ hàng
//        model.addAttribute("sell",productDetailsService.getOneProdcut(id));
        return "website/index/giohang";


}
@GetMapping("add/{id}")
public String addCart(@PathVariable("id") Integer id){
    ProductDetails productDetails=productDetailsService.getByIds(id);
//    ProductDetails productDetails1=new ProductDetails();
    if (productDetails != null){
        CartItem item =new CartItem();
        item.setId(productDetails.getId());
        item.setProductDetails(productDetails);
        item.setQuantity(1);
        cartService.add(item);
    }
        return "redirect:/cart/view-cart";
}
    @GetMapping("/display")
    public ResponseEntity<byte[]> getImage(@RequestParam("id") Integer productId) throws SQLException {
        List<Image> imageList= imageService.getImagesForProducts(productId);
        byte[] imageBytes = null;
        imageBytes = imageList.get(0).getUrl().getBytes(1, (int) imageList.get(0).getUrl().length());
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);

    }
}
