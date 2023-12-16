package com.example.datnsd56.use;

import com.example.datnsd56.config.Config;
//import com.example.datnsd56.controller.VnpayUtils;
import com.example.datnsd56.dto.TransactionInfo;
import com.example.datnsd56.dto.TransactionInfoDTO;
import com.example.datnsd56.entity.*;
import com.example.datnsd56.repository.AddressRepository;
import com.example.datnsd56.service.*;
import com.example.datnsd56.service.impl.PaymentServiceImpl;
import com.example.datnsd56.service.impl.TransactionsServiceIpml;
import com.fasterxml.jackson.databind.deser.impl.CreatorCandidate;
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
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private CartService cartServicel;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private PaymentServiceImpl paymentService;

    //    @Autowired
//    private VnpayUtils vnpayUtils;
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
    public String addOrder(@RequestParam(name = "selectedAddressRadio", required = false) Integer selectedAddressId,
                           @RequestParam(name = "paymentMethod") String paymentMethod,
                           Principal principal,
                           RedirectAttributes attributes,
                           HttpSession session,
                           Model model) {
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
                Optional<Address> selectedAddressOptional = addressService.findAccountAddresses(account.getId())
                    .stream()
                    .filter(addr -> addr.getId().equals(selectedAddressId))
                    .findFirst();

                address = selectedAddressOptional.orElse(null);
            } else {
                address = addressService.findDefaultAddress(account.getId());
            }

            if (address != null) {
                // Thực hiện đặt hàng
                Orders order = ordersService.planceOrder(cart, String.valueOf(address));
                if (order != null) {
                    // Kiểm tra xem phương thức thanh toán là VNPAY hay không
                    if ("vnpay".equals(paymentMethod)) {
                        // Tạo và lưu thông tin thanh toán vào bảng Transactions
                        Transactions transaction = new Transactions();
                        transaction.setAmount(order.getTotal());
                        transaction.setCreateDate(LocalDate.now());
                        transaction.setUpdateDate(LocalDate.now());
                        transaction.setPaymentMethod(paymentMethod);
                        transaction.setStatus("pending"); // Bạn có thể sử dụng trạng thái "pending" và cập nhật sau khi thanh toán thành công
                        transaction.setCustomerId(order.getCustomerId());
                        transaction.setOrderId(order);
                        transaction.setAccountId(account.getCart().getAccountId());



                        // Xóa giỏ hàng
                        if(transaction.getStatus().equals("pending")){
                        return "redirect:/user/cart";
                        }else {
                            transactionService.saveTransaction(transaction);
                            cartServicel.deleteCartById(cart.getId());

                        }

                        // Tạo URL thanh toán VNPAY và chuyển hướng đến trang thanh toán
                        String vnpPaymentUrl = paymentService.createVnpPaymentUrl(order.getTotal(), order.getId(), transaction.getId());
                        return "redirect:" + vnpPaymentUrl;
                    }
                    if ("cashOnDelivery".equals(paymentMethod)) {
                        // Tạo và lưu thông tin thanh toán vào bảng Transactions
                        Transactions transaction = new Transactions();
                        transaction.setAmount(order.getTotal());
                        transaction.setCreateDate(LocalDate.now());
                        transaction.setUpdateDate(LocalDate.now());
                        transaction.setPaymentMethod(paymentMethod);
                        transaction.setStatus("susccuses"); // Bạn có thể sử dụng trạng thái "pending" và cập nhật sau khi thanh toán thành công
                        transaction.setCustomerId(order.getCustomerId());
                        transaction.setOrderId(order);
                        transaction.setAccountId(account.getCart().getAccountId());

                        transactionService.saveTransaction(transaction);

                        // Xóa giỏ hàng
                        cartServicel.deleteCartById(cart.getId());
                        return "redirect:/user/transaction-success/" + order.getId();

                        // Tạo URL thanh toán VNPAY và chuyển hướng đến trang thanh toán
                    }

                    // Các logic xử lý khác, ví dụ lưu đơn hàng và xóa giỏ hàng
                    // ...

                    // Hiển thị trang thông tin giao dịch thành công
                    attributes.addFlashAttribute("order", order);
                    return "redirect:/user/transaction-success/" + order.getId();
                } else {
                    attributes.addFlashAttribute("error", "Đặt hàng không thành công. Vui lòng thử lại sau!");
                }
            } else {
                attributes.addFlashAttribute("error", "Không tìm thấy địa chỉ. Vui lòng thử lại sau!");
            }
        } else {
            return "redirect:/login";
        }

        return "redirect:/user/checkout";
    }


    @GetMapping("/transaction-success/{orderId}")
    public String transactionSuccess(@PathVariable("orderId") Integer orderId, Model model) {
        // Lấy thông tin đơn hàng từ orderId và đưa vào model để hiển thị
        Optional<Orders> orderOptional = ordersService.getOrderId(orderId);

        if (orderOptional.isPresent()) {
            Orders order = orderOptional.get();
            model.addAttribute("order", order);

            // Chuyển đến trang thông tin giao dịch thành công
            return "/website/index/giaodich";
        } else {
            // Đơn hàng không tồn tại, có thể xử lý thông báo lỗi hoặc chuyển hướng đến trang lỗi khác
            return "redirect:/error";
        }
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


    @GetMapping("/vnpay-ipn")
    public String vnpayIPN(@RequestParam("vnp_ResponseCode") String responseCode,

                           HttpServletRequest request,
                           Model model) {
        // Xử lý phản hồi từ VNP và hiển thị thông tin giao dịch
        String vnp_OrderInfo = request.getParameter("vnp_OrderInfo");
        String vnp_TransactionNo = request.getParameter("vnp_TransactionNo");
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");
        String vnp_Amount = request.getParameter("vnp_Amount");
        String vnp_BankCode = request.getParameter("vnp_BankCode");

        //... (Thêm các tham số khác cần thiết)
        if ("00".equals(responseCode)) {

            model.addAttribute("vnp_OrderInfo", vnp_OrderInfo);
            model.addAttribute("vnp_TransactionNo", vnp_TransactionNo);
            model.addAttribute("vnp_TxnRef", vnp_TxnRef);
            model.addAttribute("vnp_Amount", vnp_Amount);
            model.addAttribute("vnp_BankCode", vnp_BankCode);

            //... (Thêm các thuộc tính khác cần thiết)

            // Gọi view để hiển thị thông tin giao dịch
            return "website/index/f";
        } else {
            // Xử lý khi không tìm thấy giao dịch
            return "website/index/payment-failure";
        }
    }
}
            // Xử lý khi mã phản hồi không là "00"


        // Hiển thị thông tin giao dịch trên trang
//        return null;


//    @GetMapping("/dsdonhang")
//    public String viewdsdonhang() {
//
//        return "website/index/danhsachdonhang";
//    }


