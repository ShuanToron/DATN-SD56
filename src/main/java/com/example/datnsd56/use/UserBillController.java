package com.example.datnsd56.use;

import com.example.datnsd56.entity.Account;
import com.example.datnsd56.entity.Address;
import com.example.datnsd56.entity.Cart;
import com.example.datnsd56.entity.Orders;
import com.example.datnsd56.service.AccountService;
import com.example.datnsd56.service.AddressService;
import com.example.datnsd56.service.OrdersService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserBillController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private AddressService service;

    @GetMapping("/checkout")
    public String checkout(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }
        Optional<Account> account = accountService.finByName(principal.getName());
        Cart cart = account.get().getCart();
        Address defaultAddress = service.findAccountDefaultAddress(account.get().getId());
        if (defaultAddress == null) {
            return "redirect:/user/cart";
        }
        model.addAttribute("cart", cart);
        model.addAttribute("defaultAddress", defaultAddress);
        return "website/index/giohang1";

    }

    @GetMapping("/orders")
    public String getOrders(Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Optional<Account> account = accountService.finByName(principal.getName());
        List<Orders> listOrder = ordersService.getAllOrders1(account.get().getId());
        model.addAttribute("orders", listOrder);
        return "website/index/danhsachdonhang";
    }

    @GetMapping("/order-detail/{id}")
    public String getOrderDetail(@PathVariable Integer id, Model model, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Orders bill = ordersService.getOneBill(id);
        model.addAttribute("order", bill);
        return "website/index/danhsachdonhangdetail";
    }


    @PostMapping("/add-order")
    public String createOrder(Principal principal,
                              RedirectAttributes attributes,
                              HttpSession session,
                              @RequestParam("address") String address,
                              HttpServletRequest request) throws IOException, URISyntaxException {
        if (principal == null) {
            return "redirect:/login";
        }
        Optional<Account> account = accountService.finByName(principal.getName());
        Cart cart = account.get().getCart();

        ordersService.planceOrder(cart, address);
        session.removeAttribute("totalItems");
        attributes.addFlashAttribute("success", "Đặt hàng thành công!");
        return "redirect:/user/orders";
    }

    @GetMapping("/cancel-order/{id}")
    public String cancelOrder(@PathVariable Integer id, RedirectAttributes attributes,Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        Optional<Account> account = accountService.finByName(principal.getName());
        ordersService.cancelOrder(id);
        attributes.addFlashAttribute("success", "Huỷ đơn hàng thành công!");
        return "redirect:/user/orders";
    }

//    @GetMapping("/dsdonhang")
//    public String viewdsdonhang() {
//
//        return "website/index/danhsachdonhang";
//    }

}
