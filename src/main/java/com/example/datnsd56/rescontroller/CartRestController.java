//package com.example.datnsd56.rescontroller;
//
//import com.example.datnsd56.entity.Account;
//import com.example.datnsd56.entity.Cart;
//import com.example.datnsd56.entity.Orders;
//import com.example.datnsd56.security.UserInfoUserDetails;
//import com.example.datnsd56.service.AccountService;
//import com.example.datnsd56.service.CartService;
//import com.example.datnsd56.service.ColorService;
//import com.example.datnsd56.service.ImageService;
//import com.example.datnsd56.service.OrderItemService;
//import com.example.datnsd56.service.OrdersService;
//import com.example.datnsd56.service.ProductDetailsService;
//import com.example.datnsd56.service.SizeService;
//import com.fasterxml.jackson.databind.JsonNode;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Optional;
//
//@RestController
//public class CartRestController {
//    @Autowired
//    private CartService cartService;
//    @Autowired
//    private ImageService imageService;
//    @Autowired
//    private ColorService colorService;
//    @Autowired
//    private ProductDetailsService productDetailsService;
//    @Autowired
//    private UserInfoUserDetails userInfoUserDetails;
//    @Autowired
//    private SizeService sizeService;
//    @Autowired
//    private OrdersService ordersService;
//    @Autowired
//    private AccountService accountService;
//
//    @Transactional
//    @PostMapping("/rest/order/add")
//    public ResponseEntity<?> create(@RequestBody JsonNode orderData){
//            Optional<Account> nguoiDung = accountService.finByName(userInfoUserDetails.getName());
//            if (nguoiDung.isPresent()) {
//                Cart gioHang = cartService.findByNguoiDungId(nguoiDung.get().getId());
//                if (gioHang == null) {
//                    gioHang = new Cart();
//                    gioHang.setAccountId(nguoiDung.get());
//                    gioHang.setStatus("0");
//                    cartService.add1(gioHang);
//                }
//                if (gioHang != null) {
//
//                    cartService.remove(gioHang.getId());
//                    return  ResponseEntity.ok(ordersService.create(orderData));
//                }
//            }
//            return ResponseEntity.status(400).body(null);
//
//        }
//
//    }
