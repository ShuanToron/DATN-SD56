package com.example.datnsd56.use;


import com.example.datnsd56.entity.*;
import com.example.datnsd56.repository.AddressRepository;
import com.example.datnsd56.repository.VoucherUsageRepository;
import com.example.datnsd56.service.*;

import com.example.datnsd56.service.impl.OrderServiceImplV2;
import com.example.datnsd56.service.impl.PaymentServiceImpl;
import com.example.datnsd56.service.impl.VoucherSeviceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.HashMap;
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
private VoucherUsageRepository voucherUsageRepository;
    @Autowired
    private CartService cartServicel;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private PaymentServiceImpl paymentService;
@Autowired
private VoucherService voucherService;
@Autowired
private OrderSeriveV2 orderServiceImplV2;
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
//    @PostMapping("/apply-voucher")
@PostMapping("/apply-voucher")
public String applyVoucher(
    @RequestParam(name = "promoCode") String voucherCode,
    Principal principal,
    RedirectAttributes redirectAttributes) {

    // Kiểm tra xem người dùng đã đăng nhập hay chưa
    if (principal == null) {
        // Xử lý trường hợp người dùng chưa đăng nhập
        return "redirect:/login";
    }

    // Kiểm tra xem mã voucher đã được sử dụng chưa

    // Gọi service để kiểm tra và áp dụng voucher
    boolean appliedSuccessfully = ordersService.applyVoucher(principal.getName(), voucherCode);

    if (appliedSuccessfully) {
        // Lấy giá tiền mới sau khi áp dụng voucher
        BigDecimal newTotal = ordersService.getNewTotalAfterApplyingVoucher(principal.getName());

        // Thêm thông tin giảm giá và giá tiền mới vào flash attribute để truyền qua trang thanh toán
        redirectAttributes.addFlashAttribute("voucherApplied", true);
        redirectAttributes.addFlashAttribute("newTotal", newTotal);


    } else {
        // Thông báo lỗi nếu mã voucher không hợp lệ
        redirectAttributes.addFlashAttribute("voucherError", "Mã voucher không hợp lệ");
    }

    // Chuyển hướng trở lại trang thanh toán
    return "redirect:/user/checkout";
}

    @PostMapping("/add-order")
    public String placeOrder(@RequestParam(name = "selectedAddressRadio", required = false) Integer selectedAddressId,
                             @RequestParam(name = "paymentMethod") String paymentMethod,
                             @RequestParam(name = "promoCode", required = false) String voucherCode,
                             Principal principal,
                             RedirectAttributes attributes,
                             HttpSession session,
                             HttpServletRequest request,
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
                Orders order = orderServiceImplV2.placeOrder(cart, String.valueOf(address), voucherCode);
                if (order != null) {
                    // Kiểm tra xem phương thức thanh toán là VNPAY hay không
                    if ("vnpay".equals(paymentMethod)) {
                        // Tạo và lưu thông tin thanh toán vào bảng Transactions
                        Transactions transaction = new Transactions();
                        transaction.setAmount(order.getTotal());
                        transaction.setCreateDate(LocalDate.now());
                        transaction.setUpdateDate(LocalDate.now());
                        transaction.setPaymentMethod(paymentMethod);
                        transaction.setStatus("pending");
                        transaction.setCustomerId(order.getCustomerId());
                        transaction.setOrderId(order);
                        transaction.setAccountId(account.getCart().getAccountId());

                        // Lưu giao dịch tạm thời vào cơ sở dữ liệu và giữ ID
                        Transactions savedTransaction = transactionService.saveTransaction(transaction);

                        // Lưu ID của giao dịch vào session để sử dụng sau này
                        session.setAttribute("pendingTransactionId", savedTransaction.getId());

                        // Tạo URL thanh toán VNPAY và chuyển hướng đến trang thanh toán
                        String vnpPaymentUrl = paymentService.createVnpPaymentUrl(order.getTotal(), order.getId(), savedTransaction.getId());
                        return "redirect:" + vnpPaymentUrl;
                    }

                    if ("cashOnDelivery".equals(paymentMethod)) {
                        // Tạo và lưu thông tin thanh toán vào bảng Transactions
                        Transactions transaction = new Transactions();
                        transaction.setAmount(order.getTotal());
                        transaction.setCreateDate(LocalDate.now());
                        transaction.setUpdateDate(LocalDate.now());
                        transaction.setPaymentMethod(paymentMethod);
                        transaction.setStatus("success"); // Điều chỉnh trạng thái thành công
                        transaction.setCustomerId(order.getCustomerId());
                        transaction.setOrderId(order);
                        transaction.setAccountId(account.getCart().getAccountId());
                        transactionService.saveTransaction(transaction);

                        // Xóa giỏ hàng
                        cartServicel.deleteCartById(cart.getId());
                        return "redirect:/user/transaction-success/" + order.getId();
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
                           Model model,
                           HttpSession session) {
        // Xử lý phản hồi từ VNP và hiển thị thông tin giao dịch
        String vnp_TxnRef = request.getParameter("vnp_TxnRef");

        // Kiểm tra xem có ID của giao dịch trong session không
        Integer transactionId = (Integer) session.getAttribute("pendingTransactionId");

        if (transactionId != null) {
            // Lấy giao dịch tạm thời từ cơ sở dữ liệu
            Optional<Transactions> transactionOptional = transactionService.getTransactionsById(transactionId);

            if (transactionOptional.isPresent()) {
                Transactions pendingTransaction = transactionOptional.get();

                if ("00".equals(responseCode)) {
                    // Cập nhật thông tin giao dịch và đổi trạng thái thành "success"
                    pendingTransaction.setOrderInfo(vnp_TxnRef);
                    pendingTransaction.setStatus("success");
                    transactionService.saveTransaction(pendingTransaction);

                    // ... (Thêm các thuộc tính khác cần thiết)

                } else {
                    // Xử lý khi thanh toán không thành công
                    pendingTransaction.setOrderInfo(vnp_TxnRef);
                    pendingTransaction.setStatus("fail");
                    transactionService.saveTransaction(pendingTransaction);

                    return "website/index/payment-failure";

                    // ... (Thêm các thuộc tính khác cần thiết)
                }

                // Lưu thông tin giao dịch vào cơ sở dữ liệu
                transactionService.saveTransaction(pendingTransaction);

                // Xóa ID của giao dịch khỏi session
                session.removeAttribute("pendingTransactionId");

                // Gọi view để hiển thị thông tin giao dịch
                return "website/index/f";
            }
        }

        // Xử lý khi không tìm thấy thông tin giao dịch tạm thời hoặc gặp lỗi khác
        return "website/index/payment-failure";
    }
}
        // Hiển thị thông tin giao dịch trên trang
//        return null;


//    @GetMapping("/dsdonhang")
//    public String viewdsdonhang() {
//
//        return "website/index/danhsachdonhang";
//    }


