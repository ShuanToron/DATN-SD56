//package com.example.datnsd56.rescontroller;
//
//import com.example.datnsd56.entity.Account;
//import com.example.datnsd56.entity.Cart;
//import com.example.datnsd56.entity.CartItem;
////import com.example.datnsd56.entity.OrderItem;
//import com.example.datnsd56.entity.ProductDetails;
//import com.example.datnsd56.service.AccountService;
//import com.example.datnsd56.service.CartDetailService;
//import com.example.datnsd56.service.CartService;
//import com.example.datnsd56.service.ColorService;
//import com.example.datnsd56.service.ImageService;
//import com.example.datnsd56.service.OrdersService;
//import com.example.datnsd56.service.ProductDetailsService;
//import com.example.datnsd56.service.SizeService;
//import com.fasterxml.jackson.databind.JsonNode;
//import jakarta.servlet.http.HttpSession;
//import jakarta.transaction.Transactional;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Collections;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@RequestMapping("/rest")
//@RestController
//public class OrderRestController {
//    @Autowired
//    private CartService cartService;
//    @Autowired
//    private ColorService colorService;
//    @Autowired
//    private CartDetailService cartDetailService;
//    @Autowired
//    private ProductDetailsService productDetailsService;
//    @Autowired
//    private SizeService sizeService;
//    @Autowired
//    private OrdersService ordersService;
//    @Autowired
//    private AccountService accountService;
//
//    @GetMapping("/chitietgiohang")
//    public List<CartItem> getChiTietGioHang(HttpSession session, @PathVariable("id") Integer id) {
//        Optional<Account> nguoiDung = accountService.findById(id);
//
//
//        if (nguoiDung.isPresent()) {
//            Cart gioHang = cartService.findByNguoiDungId(nguoiDung.get().getId());
//
//            if (gioHang != null) {
//
//                List<CartItem> chiTietGioHang = cartDetailService.findAllByIdGioHang(gioHang.getId());
//
//
//                return chiTietGioHang;
//            }
//        }
//
//
//            return Collections.emptyList();
//
//}
//
//
//    @PostMapping("/cart/add")
//    public ResponseEntity<?> handleSelectedData(
//            @RequestBody JsonNode orderData ,@PathVariable("id") Integer id) {
//
//        String productIdString = orderData.get("productId").asText(); // Lấy giá trị UUID dưới dạng String
//        Integer productId = Integer.valueOf(productIdString); // Chuyển String sang UUID
//
//
//        Optional<Account> nguoiDung = accountService.findById(id);
//
//
//        if (nguoiDung != null && productId != null) {
//            Cart gioHang = cartService.findByNguoiDungId(nguoiDung.get().getId());
//
//            if (gioHang == null) {
//                gioHang = new Cart();
//                gioHang.setAccountId(nguoiDung.get());
//                gioHang.setStatus("0");
//                cartService.add1(gioHang);
//            }
//
//            Optional<ProductDetails> chiTietSanPham = productDetailsService.findBySanPhamId(productId);
//
//            if (chiTietSanPham.isPresent()) {
//                CartItem chiTietGioHang = cartDetailService.findAllByIdGioHang(gioHang.getId()).stream()
//                        .filter(ctgh -> ctgh.getProductDetails().getId().equals(chiTietSanPham.get().getId()))
//                        .findFirst()
//                        .orElse(null);
//
//                if (chiTietGioHang != null) {
//                    chiTietGioHang.setQuantity(chiTietGioHang.getQuantity() + 1);
//                } else {
//                    CartItem cartItem = new CartItem();
//                    cartItem.setCart(gioHang);
//                    cartItem.setProductDetails(chiTietSanPham.get());
//                    cartItem.setQuantity(1);
//                    chiTietGioHang = cartItem;
//                }
//
//                cartService.add(chiTietGioHang);
//
//                // Cập nhật lại số lượng của ChiTietSanPham
//                chiTietSanPham.get().setQuantity(chiTietSanPham.get().getQuantity() - 1);
//                productDetailsService.save(chiTietSanPham.get());
//
//                return ResponseEntity.ok(chiTietGioHang);
//            }
//        }
//
//
//        return ResponseEntity.status(400).body(null);
//    }
//
//
//    @PostMapping("/cart/update")
//    public ResponseEntity<?> update(
//            @RequestBody JsonNode orderData) {
//
//        String productIdString = orderData.get("productId").asText(); // Lấy giá trị UUID dưới dạng String
//        Integer productId = Integer.valueOf(productIdString); // Chuyển String sang UUID
//        int quantity = orderData.get("quantity").asInt(); // Lấy giá trị số lượng
//
//        if (productId != null && quantity >= 0) {
//            Optional<CartItem> chiTietGioHang = cartDetailService.findById(productId);
//            if (chiTietGioHang.isPresent()) {
//                Optional<ProductDetails> chiTietSanPham = productDetailsService.findById(chiTietGioHang.get().getProductDetails().getId());
//                int soLuongThayDoi = chiTietGioHang.get().getQuantity() - quantity;
//
//                if (quantity == 0) {
//                    chiTietSanPham.get().setQuantity(chiTietSanPham.get().getQuantity() + soLuongThayDoi);
//                    productDetailsService.save(chiTietSanPham.get());
//                    cartDetailService.deleteById(productId);
//                    return ResponseEntity.ok("Updated and Deleted successfully");
//                }
//
//                chiTietGioHang.get().setQuantity(quantity);
//                CartItem chiTietGioHang1 = cartDetailService.save(chiTietGioHang.get());
//                chiTietSanPham.get().setQuantity(chiTietSanPham.get().getQuantity() + soLuongThayDoi);
//                productDetailsService.save(chiTietSanPham.get());
//
//                return ResponseEntity.ok(chiTietGioHang1);
//            }
//        }
//        return ResponseEntity.status(400).body("Invalid request data");
//    }
//
//
//    @Transactional
//    @PostMapping("/cart/remove")
//    public void remove(
//            @RequestBody JsonNode orderData) {
//
//        String productIdString = orderData.get("productId").asText(); // Lấy giá trị UUID dưới dạng String
//        Integer productId = Integer.valueOf(productIdString); // Chuyển String sang UUID
//
//        if (productId != null) {
//            Optional<CartItem> chiTietGioHang = cartDetailService.findById(productId);
//
//            if (chiTietGioHang.isPresent()) {
//                ProductDetails chiTietSanPham = chiTietGioHang.get().getProductDetails();
//                int soLuongThayDoi = chiTietGioHang.get().getQuantity();
//
//                // Cập nhật số lượng của ChiTietSanPham
//                chiTietSanPham.setQuantity(chiTietSanPham.getQuantity() + soLuongThayDoi);
//                productDetailsService.save(chiTietSanPham);
//
//                // Xóa ChiTietGioHang
//                cartDetailService.deleteById(productId);
//            }
//        }
//
//
//    }
//
//
//    @Transactional
//    @PostMapping("/cart/clear")
//    public void clearCart(@PathVariable("id") Integer id) {
//        Optional<Account> nguoiDung = accountService.findById(id);
//
//
//        if (nguoiDung.isPresent()) {
//            Cart gioHang = cartService.findByNguoiDungId(nguoiDung.get().getId());
//
//            if (gioHang != null) {
//                List<CartItem> chiTietGioHangs = cartDetailService.findAllByIdGioHang(gioHang.getId());
//
//                for (CartItem chiTietGioHang : chiTietGioHangs) {
//                    ProductDetails chiTietSanPham = chiTietGioHang.getProductDetails();
//                    int soLuongHienTai = chiTietSanPham.getQuantity();
//                    int soLuongThem = chiTietGioHang.getQuantity();
//                    chiTietSanPham.setQuantity(soLuongHienTai + soLuongThem);
//                    productDetailsService.save(chiTietSanPham);
//                }
//
//                // Xóa tất cả ChiTietGioHang sau khi cập nhật thành công ChiTietSanPham
//                cartDetailService.deleteAllByGioHang_Id(gioHang.getId());
//
//            }
//        }
//
//    }
//
//
//}
//
//
