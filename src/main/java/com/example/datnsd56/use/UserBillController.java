package com.example.datnsd56.use;

import com.example.datnsd56.entity.Account;
import com.example.datnsd56.entity.Address;
import com.example.datnsd56.entity.Cart;
import com.example.datnsd56.entity.Orders;
import com.example.datnsd56.repository.AddressRepository;
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
    @Autowired
    private AddressRepository addressRepository;
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
                // Nếu không có địa chỉ, hiển thị trang giỏ hàng
                model.addAttribute("cart", cart);
                model.addAttribute("addressList", addressList);
                model.addAttribute("newAddress", new Address()); // Thêm đối tượng mới cho modal
                model.addAttribute("defaultAddress", null); // Không có địa chỉ mặc định
                return "website/index/giohang1";
            } else {
                Address defaultAddress = addressList.stream()
                    .filter(address -> Boolean.TRUE.equals(address.getDefaultAddress()))
                    .findFirst()
                    .orElse(null);

                model.addAttribute("cart", cart);
                model.addAttribute("addressList", addressList);
                model.addAttribute("newAddress", new Address()); // Thêm đối tượng mới cho modal
                model.addAttribute("defaultAddress", defaultAddress); // Địa chỉ mặc định
                return "website/index/giohang1";
            }
        }
        return "redirect:/login";
    }

    @PostMapping("/setDefaultAddress")
    public String setDefaultAddress(@RequestParam("addressId") Integer addressId, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }

        Optional<Account> accountOptional = accountService.finByName(principal.getName());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            // Đặt địa chỉ có addressId làm địa chỉ mặc định cho tài khoản
            addressService.setDefaultAddress(account, addressId);
        }

        return "redirect:/user/checkout";
    }

    @PostMapping("/add1")
    public ModelAndView add1(@Valid @ModelAttribute("newAddress") Address newAddress,
                             BindingResult result, HttpSession session, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        if (result.hasErrors()) {
            // Nếu có lỗi, chuyển về trang thanh toán với model chứa thông tin giỏ hàng và danh sách địa chỉ
            Optional<Account> accountOptional = accountService.finByName(principal.getName());
            if (accountOptional.isPresent()) {
                Account account = accountOptional.get();
                Cart cart = account.getCart();
                modelAndView.addObject("cart", cart);

                List<Address> accountAddresses = addressService.findAccountAddresses(account.getId());
                modelAndView.addObject("accountAddresses", accountAddresses);

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
                    Address savedAddress = addressService.addNewAddress(account, newAddress, newAddress.getDefaultAddress());

                    // Thực hiện đặt hàng với địa chỉ mới
                    // ...

                    session.setAttribute("successMessage", "Thêm thành công");
                    modelAndView.setViewName("redirect:/user/checkout");
                }
            } else {
                modelAndView.setViewName("redirect:/login");
            }
        }

        return modelAndView;
    }
// Các phương thức khác...
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
                           @RequestParam(name = "selectedAddressRadio", required = false) Integer selectedAddressId) {
        if (principal == null) {
            return "redirect:/login";
        }

        Optional<Account> accountOptional = accountService.finByName(principal.getName());
        if (accountOptional.isPresent()) {
            Account account = accountOptional.get();
            Cart cart = account.getCart();

            // Kiểm tra xem người dùng đã chọn địa chỉ từ danh sách hay không
            Address address;
            if (selectedAddressId != null) {
                // Sử dụng địa chỉ từ danh sách
                Optional<Address> selectedAddressOptional = addressService.findAccountAddresses(account.getId())
                    .stream()
                    .filter(addr -> addr.getId().equals(selectedAddressId))
                    .findFirst();

                address = selectedAddressOptional.orElse(null);
            } else {
                // Lấy địa chỉ mặc định
                address = addressService.findDefaultAddress(account.getId());
            }

            if (address != null) {
                // Thực hiện đặt hàng
                Orders order = ordersService.planceOrder(cart, String.valueOf(address));

                if (order != null) {
                    session.removeAttribute("totalItems");
                    attributes.addFlashAttribute("success", "Đặt hàng thành công!");
                } else {
                    attributes.addFlashAttribute("error", "Đặt hàng không thành công. Vui lòng thử lại sau!");
                }
            } else {
                attributes.addFlashAttribute("error", "Không tìm thấy địa chỉ. Vui lòng thử lại sau!");
            }

            return "redirect:/user/orders";
        } else {
            return "redirect:/login";
        }
    }





//    @GetMapping("/dsdonhang")
//    public String viewdsdonhang() {
//
//        return "website/index/danhsachdonhang";
//    }

}
