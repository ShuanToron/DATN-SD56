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
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
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
    @Autowired
    private AddressService addressService;

    @GetMapping("/checkout")
    public String checkout(Principal principal, Model model) {
        if (principal == null) {
            return "redirect:/login";
        }

        Optional<Account> accountOptional = accountService.finByName(principal.getName());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Cart cart = account.getCart();

            // Tìm địa chỉ mặc định
            List<Address> addressList = addressService.findAccountAddresses(account.getId());

            if (addressList.isEmpty()) {
                // Nếu không có địa chỉ, chuyển hướng đến trang thêm địa chỉ mới
                return "website/index/giohang1";

            } else {
                model.addAttribute("cart", cart);
                model.addAttribute("addressList", addressList);
                model.addAttribute("newAddress", new Address()); // Thêm đối tượng mới cho modal
                return "website/index/giohang1";
            }
        }
        return "redirect:/login";


    // ... (Các phương thức và mã code khác)
}

    @PostMapping("/add1")
    public ModelAndView add1(@Valid @ModelAttribute("newAddress") Address newAddress,
                             BindingResult result, Model model, HttpSession session, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            // Nếu có lỗi, chuyển về trang thanh toán với model chứa thông tin giỏ hàng và danh sách địa chỉ
            Optional<Account> accountOptional = accountService.finByName(principal.getName());
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                Cart cart = account.getCart();
                model.addAttribute("cart", cart);

                List<Address> accountAddresses = service.findAccountAddresses(account.getId());
                model.addAttribute("accountAddresses", accountAddresses);

                modelAndView.setViewName("website/index/giohang1");
            } else {
                modelAndView.setViewName("redirect:/login");
            }
        } else {
            Optional<Account> accountOptional = accountService.finByName(principal.getName());
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();

                // Kiểm tra xem có địa chỉ được chọn từ danh sách không
                if (newAddress.getId() != null) {
                    Address selectedAddress = service.findAccountDefaultAddress(newAddress.getId());
                    // Thực hiện đặt hàng với địa chỉ đã chọn
                    // ...

                } else {
                    // Nếu không có địa chỉ được chọn, sử dụng địa chỉ mới từ form
                    Address savedAddress = addressService.addNewAddress(account, newAddress);
                    // Thực hiện đặt hàng với địa chỉ mới
                    // ...
//                    model.addAttribute("newAddress", new Address());


                }

                session.setAttribute("successMessage", "Thêm thành công");
                modelAndView.setViewName("redirect:/user/checkout");
            } else {
                modelAndView.setViewName("redirect:/login");
            }
        }

        return modelAndView;
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

    @PostMapping("/add-order")
    public String addOrder(Principal principal,
                           RedirectAttributes attributes,
                           HttpSession session,
                           @ModelAttribute("newAddress") Address newAddress,
                           @RequestParam(name = "selectedAddress", required = false) Integer selectedAddress) {
        if (principal == null) {
            return "redirect:/login";
        }

        Optional<Account> accountOptional = accountService.finByName(principal.getName());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Cart cart = account.getCart();

            // Kiểm tra xem người dùng đã chọn địa chỉ từ danh sách hay nhập địa chỉ mới
            Address address;
            if (selectedAddress != null) {
                // Sử dụng địa chỉ từ danh sách
                Optional<Address> selectedAddressOptional = addressService.findAccountAddresses(account.getId())
                    .stream()
                    .filter(addr -> addr.getId().equals(selectedAddress))
                    .findFirst();

                address = selectedAddressOptional.orElse(null);
            } else {
                // Sử dụng địa chỉ mới từ form
                address = addressService.addNewAddress(account, newAddress);
            }

            if (address != null) {
                // Thực hiện đặt hàng
                Orders order = ordersService.planceOrder(cart, String.valueOf(address));

                if (order != null) {
                    session.removeAttribute("totalItems");
                    attributes.addFlashAttribute("success", "Đặt hàng thành công!");
                } else {
                    // Xử lý trường hợp đặt hàng thất bại
                    attributes.addFlashAttribute("error", "Đặt hàng không thành công. Vui lòng thử lại sau!");
                }
            } else {
                // Xử lý trường hợp không tìm thấy địa chỉ
                attributes.addFlashAttribute("error", "Không tìm thấy địa chỉ. Vui lòng thử lại sau!");
            }

            return "redirect:/user/orders";
        } else {
            // Xử lý trường hợp không tìm thấy tài khoản
            return "redirect:/login";
        }
    }





//    @GetMapping("/dsdonhang")
//    public String viewdsdonhang() {
//
//        return "website/index/danhsachdonhang";
//    }

}
