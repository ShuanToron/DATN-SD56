
package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.*;
import com.example.datnsd56.repository.*;
import com.example.datnsd56.security.UserInfoUserDetails;
import com.example.datnsd56.service.CartService;
import com.example.datnsd56.service.OrdersService;
import com.example.datnsd56.service.VoucherService;
import com.example.datnsd56.service.VoucherUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private TransactionsRepository transactionsRepository;
    @Autowired
    private UserInfoUserDetails userInfoUserDetails;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;
    @Autowired
    private VoucherSeviceImpl voucherService;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private VoucherUsageRepository voucherUsageRepository;
    @Autowired
    private TransactionsServiceIpml transactionsService;
@Autowired
private VoucherUsageService voucherUsageService;
    @Autowired
    public void OrdersService(OrdersRepository ordersRepository, TransactionsRepository transactionsRepository) {
        this.ordersRepository = ordersRepository;
        this.transactionsRepository = transactionsRepository;
    }

    @Override
    public Orders detailHD(Integer id) {
        return ordersRepository.findById(id).orElse(null);
    }

    @Override
    public List<Orders> getAll() {
        return ordersRepository.findAllByOrderStatus("0");
    }

    @Override
    public Page<Orders> getAllOrders(Integer page) {
        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "OrderDate"));
        return ordersRepository.findAllByOrderStatus(pageable, "0");
    }

    @Override
    public Transactions placeOrder(Cart cart, String address, String paymentMethod) {
        return null;
    }


    @Override
    public void delete(Integer id) {
        ordersRepository.deleteById(id);
    }

    @Override
    public Orders update(Orders orders, Integer id) {
        Optional<Orders> optional = ordersRepository.findById(id);
        if (optional.isPresent()) {
            Orders orders1 = optional.get();
            orders.setId(orders1.getId());
            orders.setOrderStatus(orders1.getOrderStatus());
            orders.setCreateDate((orders1.getCreateDate()));
            orders.setUpdateDate((LocalDate.now()));
            return ordersRepository.save(orders);
        }
        return null;
    }

    @Override
    public Orders planceOrder(Cart cart, String address) {
        return null;
    }

    public boolean canUseVoucher(Account account, Voucher voucher) {
        // Kiểm tra xem voucher có được sử dụng bởi tài khoản hay không
        Set<VoucherUsage> voucherUsages = voucher.getVoucherUsages();
        for (VoucherUsage usage : voucherUsages) {
            if (usage.getAccount().equals(account)) {
                return false; // Tài khoản đã sử dụng voucher này
            }
        }
        return true; // Tài khoản chưa sử dụng voucher này
    }

    @Override
    public BigDecimal getNewTotalAfterApplyingVoucher(String username) {
        // Lấy thông tin giỏ hàng của người dùng
        Optional<Account> accountOptional = accountRepository.findByName(username);

        Cart cart = null;
        if (accountOptional.isPresent()) {
            cart = accountOptional.get().getCart();

            // Lấy thông tin voucher đã áp dụng trong giỏ hàng
            Optional<VoucherUsage> appliedVoucherOptional = voucherUsageRepository
                .findTopByAccountIdAndIsUsedFalseAndVoucher_ActiveTrueOrderByUsedDateDesc(accountOptional.get().getId());

            if (appliedVoucherOptional.isPresent()) {
                VoucherUsage appliedVoucher = appliedVoucherOptional.get();

                // Kiểm tra xem voucher có hợp lệ không
                if (voucherService.canUseVoucher(accountOptional.get(), appliedVoucher.getVoucher())) {
                    // Tính toán giảm giá từ voucher
                    BigDecimal discountValue = calculateDiscountValue(appliedVoucher.getVoucher(), cart.getTotalPrice());

                    // Tính toán giá tiền mới sau khi áp dụng voucher
                    BigDecimal discountedTotal = cart.getTotalPrice().subtract(discountValue);

                    // Kiểm tra xem giảm giá từ voucher có vượt quá tổng giá trị của đơn hàng không
                    if (discountedTotal.compareTo(BigDecimal.ZERO) < 0) {
                        return BigDecimal.ZERO;
                    }

                    return discountedTotal;
                }
            }
        }

        // Nếu không có voucher nào được áp dụng hoặc voucher không hợp lệ, trả về giá tiền hiện tại của giỏ hàng
        return cart.getTotalPrice();
    }

    @Override
    public boolean applyVoucher(String username, String voucherCode) {
        // Lấy thông tin người dùng
        Optional<Account> accountOptional = accountRepository.findByName(username);
        if (accountOptional.isEmpty()) {
            return false; // Người dùng không tồn tại
        }

        Account account = accountOptional.get();
        Cart cart = account.getCart();

        // Kiểm tra xem mã voucher có hợp lệ không
        Optional<Voucher> voucherOptional = voucherService.findByCode(voucherCode);
        if (voucherOptional.isEmpty()) {
            return false; // Mã voucher không tồn tại
        }

        Voucher voucher = voucherOptional.get();

        // Kiểm tra xem voucher có thể sử dụng cho đơn hàng không
        if (voucherService.canUseVoucher(account, voucher)) {
            // Kiểm tra xem voucher đã được sử dụng trước đó không
            Optional<VoucherUsage> existingUsage = voucherUsageRepository.findByAccountAndVoucherAndIsUsedTrue(account, voucher);
            if (existingUsage.isPresent()) {
                // Mã voucher đã được sử dụng
                return false;
            }

            // Áp dụng voucher cho giỏ hàng và cập nhật giá tiền mới
            BigDecimal discountValue = calculateDiscountValue(voucher, cart.getTotalPrice());
            BigDecimal discountedTotal = cart.getTotalPrice().subtract(discountValue);
            cart.setTotalPrice(discountedTotal);

            // Lưu thông tin sử dụng voucher vào database
            VoucherUsage voucherUsage = new VoucherUsage();
            voucherUsage.setAccount(account);
            voucherUsage.setVoucher(voucher);
            voucherUsage.setUsedDate(LocalDateTime.now());
            voucherUsage.setIsUsed(true);
            voucherUsageRepository.save(voucherUsage);

            // Cập nhật trạng thái voucher đã sử dụng
//            voucherService.updateVoucherStatus(voucher);

            return true; // Áp dụng voucher thành công
        }

        return false; // Không thể áp dụng voucher cho đơn hàng
    }



    @Override
    public Orders placeOrders(Cart cart, String address, String voucherCode) {
        Orders bill = new Orders();

        bill.setAddress(address);
        bill.setPhone(cart.getAccountId().getPhone());
        bill.setEmail(cart.getAccountId().getEmail());
        bill.setFullname(cart.getAccountId().getName());
        bill.setShippingFee(BigDecimal.ZERO);

        bill.setTotal(cart.getTotalPrice().setScale(2, RoundingMode.HALF_UP));

        bill.setOrderStatus("Chờ xác nhận");
        bill.setCreateDate(LocalDate.now());
        bill.setUpdateDate(LocalDate.now());
        bill.setAccountId(cart.getAccountId());

        try {
            bill = ordersRepository.save(bill);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            return null;
        }

        List<OrderItem> billDetailList = new ArrayList<>();
        for (CartItem item : cart.getCartItems()) {
            OrderItem billDetail = new OrderItem();
            billDetail.setOrders(bill);
            billDetail.setProductDetails(item.getProductDetails());
            billDetail.setPrice(item.getPrice().setScale(2, RoundingMode.HALF_UP));
            billDetail.setQuantity(item.getQuantity());
            billDetail.setStatus("1");

            orderItemRepository.save(billDetail);
            billDetailList.add(billDetail);

            ProductDetails productDetail = productDetailsRepository.findById(item.getProductDetails().getId()).orElse(null);
            if (productDetail != null) {
                int remainingQuantity = productDetail.getQuantity() - item.getQuantity();
                productDetail.setQuantity(remainingQuantity > 0 ? remainingQuantity : 0);
                productDetail.setStatus(remainingQuantity > 0);
                productDetailsRepository.save(productDetail);
            }
        }

        bill.setOrderItems(billDetailList);

        // Kiểm tra và áp dụng voucher nếu có
        if (voucherCode != null && !voucherCode.isEmpty()) {
            Optional<Voucher> voucherOptional = voucherService.findByCode(voucherCode);
            if (voucherOptional.isPresent()) {
                Voucher voucher = voucherOptional.get();

                // Kiểm tra xem tài khoản đã sử dụng voucher này chưa
                boolean canUseVoucher = voucherService.canUseVoucher(cart.getAccountId(), voucher);
                if (canUseVoucher) {
                    BigDecimal discountValue = calculateDiscountValue(voucher, cart.getTotalPrice());

                    // Áp dụng giảm giá và cập nhật tổng giá trị của đơn hàng
                    BigDecimal discountedTotal = cart.getTotalPrice().subtract(discountValue);
                    bill.setTotal(discountedTotal);

                    // Lưu lịch sử sử dụng voucher cho tài khoản
                    VoucherUsage voucherUsage = new VoucherUsage();
                    voucherUsage.setAccount(cart.getAccountId());
                    voucherUsage.setVoucher(voucher);
                    voucherUsage.setUsedDate(LocalDateTime.now());
                    voucherUsage.setIsUsed(true);
//                    voucherUsage.setUsedOrder(bill);

                    voucherUsageRepository.save(voucherUsage);

                    // Cập nhật voucher vào đơn hàng
                    bill.setVoucherId(voucher);


                } else {
                    // Không thể sử dụng voucher vì đã sử dụng trước đó
                    // Xử lý thông báo hoặc tạo exception tùy thuộc vào yêu cầu của bạn
                }
            }
        }
        // Lưu đơn hàng vào cơ sở dữ liệu
        try {
            bill = ordersRepository.save(bill);
        } catch (Exception e) {
            // Log the error
            e.printStackTrace();
            return null;
        }

        // Log to check if order is saved successfully
        System.out.println("Order Saved Successfully!");

        return bill;
    }

//    public BigDecimal applyVoucherAndGetTotal(Cart cart, Voucher voucherCode) {
//        Orders bill = new Orders();
//
//        // Thiết lập thông tin đơn hàng
//        bill.setAddress("Shipping Address");
//        bill.setPhone(cart.getAccountId().getPhone());
//        bill.setEmail(cart.getAccountId().getEmail());
//        bill.setFullname(cart.getAccountId().getName());
//        bill.setShippingFee(BigDecimal.ZERO);
//
//        // Tính tổng giá trị đơn hàng trước khi áp dụng voucher
//        BigDecimal totalBeforeDiscount = calculateTotal(cart,voucherCode);
//        bill.setTotal(totalBeforeDiscount.setScale(2, RoundingMode.HALF_UP));
//
//        bill.setOrderStatus("Chờ xác nhận");
//        bill.setCreateDate(LocalDate.now());
//        bill.setUpdateDate(LocalDate.now());
//        bill.setAccountId(cart.getAccountId());
//
//        // Tính tổng giá trị đơn hàng sau khi áp dụng voucher
//        BigDecimal totalAfterDiscount = totalBeforeDiscount;
//
//        List<OrderItem> billDetailList = new ArrayList<>();
//        for (CartItem item : cart.getCartItems()) {
//            OrderItem billDetail = new OrderItem();
//            // Thiết lập thông tin chi tiết đơn hàng
//            billDetail.setOrders(bill);
//            billDetail.setProductDetails(item.getProductDetails());
//            billDetail.setPrice(item.getPrice().setScale(2, RoundingMode.HALF_UP));
//            billDetail.setQuantity(item.getQuantity());
//            billDetail.setStatus("1");
//            billDetailList.add(billDetail);
//        }
//
//        bill.setOrderItems(billDetailList);
//
//        // Kiểm tra và áp dụng voucher
//        if (voucherCode != null && !voucherCode.getCode().isEmpty()) {
//            Optional<Voucher> voucher = voucherService.findByCode(String.valueOf(voucherCode));
//
//            if (voucher != null && voucher.get().isActive() && voucher.get().getUsedByAccountId() == null) {
//                BigDecimal discountAmount = totalBeforeDiscount.multiply(voucher.get().getDiscount().divide(BigDecimal.valueOf(100)));
//                totalAfterDiscount = totalBeforeDiscount.subtract(discountAmount);
//
//                // Hiển thị tổng giá tiền sau khi áp dụng voucher cho người dùng
//                System.out.println("Total Price After Applying Voucher: " + totalAfterDiscount);
//
//                voucher.get().setUsedByAccountId(cart.getAccountId());
//
//                // Lưu thông tin về voucher đã sử dụng vào cơ sở dữ liệu
//                voucherService.updateVoucher(voucher.get());
//            } else {
//                // Xử lý trường hợp voucher không hợp lệ
//                System.out.println("Invalid voucher code. Please check and try again.");
//                return null;
//            }
//        } else {
//            // Hiển thị tổng giá tiền trước khi áp dụng voucher cho người dùng
//            System.out.println("Total Price Before Applying Voucher: " + totalBeforeDiscount);
//        }
//
//        // Lưu thông tin đơn hàng vào cơ sở dữ liệu
//        try {
//            bill = ordersRepository.save(bill);
//            // Log to check if order is saved successfully
//            System.out.println("Order Saved Successfully!");
//        } catch (Exception e) {
//            // Log the error
//            e.printStackTrace();
//            return null;
//        }
//
//        return totalAfterDiscount;
//    }


    private BigDecimal calculateTotal(Cart cart, Voucher voucher) {
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : cart.getCartItems()) {
            BigDecimal itemTotal = item.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            total = total.add(itemTotal);
        }

        // Áp dụng giảm giá từ voucher nếu có
        if (voucher != null && voucher.isActive()) {
            if (voucher.getDiscountType() == DiscountType.PERCENTAGE) {
                // Giảm giá theo phần trăm
                BigDecimal discountAmount = total.multiply(voucher.getDiscount().divide(BigDecimal.valueOf(100)));
                total = total.subtract(discountAmount);
            } else if (voucher.getDiscountType() == DiscountType.AMOUNT) {
                // Giảm giá cố định
                total = total.subtract(voucher.getDiscount());
            }
        }

        return total.setScale(2, RoundingMode.HALF_UP);
    }

//    @Override
//    public Orders applyVoucherToOrder(Orders order, Voucher voucher) {
//        if (order != null && voucher != null && voucher.isActive()) {
//            // Kiểm tra xem voucher có áp dụng cho hóa đơn không
//            if (isVoucherApplicableToOrder(voucher, order)) {
//                // Áp dụng giảm giá từ voucher vào hóa đơn
//                BigDecimal discountAmount = calculateDiscountAmount(order.getTotal(), voucher);
//                order.setTotal(order.getTotal().subtract(discountAmount));  // Sử dụng subtract thay vì trừ trực tiếp
//                order.setVoucherId(voucher);
//            }
//        }
//        return order;
//    }


    private boolean isVoucherApplicableToOrder(Voucher voucher, Orders order) {
        LocalDateTime currentTime = LocalDateTime.now();

        // Kiểm tra xem voucher có hoạt động không, thời gian hiện tại có trước thời gian hết hạn không
        // và tổng giá trị đơn hàng lớn hơn hoặc bằng giảm giá voucher
        return voucher.isActive() && currentTime.isBefore(voucher.getExpiryDateTime())
            && order.getTotal().compareTo(voucher.getDiscount()) >= 0;
    }

    @Override
    public BigDecimal calculateDiscountValue(Voucher voucher, BigDecimal total) {
        if (voucher.isActive() && voucher.getStartDate().isBefore(LocalDateTime.now()) && voucher.getExpiryDateTime().isAfter(LocalDateTime.now())) {
            if (voucher.getDiscountType() == DiscountType.PERCENTAGE) {
                BigDecimal discountPercentage = voucher.getDiscount().divide(BigDecimal.valueOf(100));
                return total.multiply(discountPercentage);
            } else {
                return voucher.getDiscount();
            }
        }
        return BigDecimal.ZERO;
    }

    @Autowired
    public OrdersServiceImpl(OrdersRepository ordersRepository, OrderItemRepository orderItemRepository,
                             ProductDetailsRepository productDetailsRepository, CartService cartService) {
        this.ordersRepository = ordersRepository;
        this.orderItemRepository = orderItemRepository;
        this.productDetailsRepository = productDetailsRepository;
        this.cartService = cartService;
    }


    @Override
    public Orders add(Orders hoaDon) {
        hoaDon.setOrderStatus("1");
        hoaDon.setCreateDate(LocalDate.now());
        hoaDon.setUpdateDate(LocalDate.now());
        return ordersRepository.save(hoaDon);
    }

    @Override
    public Orders processOrder(Orders order, String voucherCode, Account account) {
        return null;
    }


    @Override
    public List<Orders> getAllOrders1(Integer accountId) {
        return ordersRepository.getAllOrders(accountId);
    }

    @Override
    public Orders applyVoucherToOrder(Orders order, Voucher voucher) {
        return null;
    }

    @Override
    public Optional<Orders> getOrderId(Integer id) {
        return ordersRepository.findById(id);
    }
}


//    public Orders planceOrder(Cart cart, String address) {
//        // Khởi tạo và thiết lập giá trị cho Orders
//        Orders bill = new Orders();
//        bill.setId(1);
//        bill.setAddress(address);
//        bill.setPhone(cart.getAccountId().getPhone());
//        bill.setEmail(cart.getAccountId().getEmail());
//        bill.setShippingFee(BigDecimal.ZERO);
//        bill.setTotal(BigDecimal.ZERO);
//        bill.setOrderStatus(1);
//        bill.setCreateDate(LocalDate.now());
//        bill.setUpdateDate(LocalDate.now());
//        bill.setAccountId(cart.getAccountId());
//
//        // Lưu Orders để có giá trị 'id' được sinh tự động
//        try {
//            bill = ordersRepository.save(bill);
//        } catch (Exception e) {
//            // Xử lý lỗi nếu không thể lưu Orders
//            return null;
//        }
//
//        // Tạo và lưu OrderItem
//        List<OrderItem> billDetailList = new ArrayList<>();
//        for (CartItem item : cart.getCartItems()) {
//            OrderItem billDetail = new OrderItem();
//            billDetail.setOrders(bill);
//            billDetail.setProductDetails(item.getProductDetails());
//            billDetail.setPrice(item.getPrice());
//            billDetail.setQuantity(item.getQuantity());
//            billDetail.setStatus("1");
//
//            // Cập nhật và lưu ProductDetails
//            ProductDetails productDetail = productDetailsRepository.findById(item.getProductDetails().getId()).orElse(null);
//            if (productDetail != null) {
//                productDetail.setQuantity(productDetail.getQuantity() - item.getQuantity());
//                if (productDetail.getQuantity() == 0) {
//                    productDetail.setStatus(true);
//                }
//                productDetailsRepository.save(productDetail);
//            }
//            billDetailList.add(billDetail);
//        }
//
//        // Thiết lập danh sách OrderItems cho Orders
//        bill.setOrderItems(billDetailList);
//
//        // Xóa Cart sau khi tạo đơn hàng thành công
//        cartService.deleteCartById(cart.getId());
//
//        return bill;
//    }

//  public Orders planceOrder(Cart cart, String address) {
//        Orders bill = new Orders();
////        Address customerAddress = addressRepository.findById(Integer.valueOf(address)).orElse(null);
//////        bill.setAccountId(customerAddress.getAccount());
////        bill.setAddress(customerAddress.getStreetName() + ", "
////                + customerAddress.getProvince() + ", "
////                + customerAddress.getDistrict() + ", "
////                + customerAddress.getZipcode());
//        bill.setAddress(address);
//        bill.setPhone(cart.getAccountId().getPhone());
//        bill.setEmail(cart.getAccountId().getEmail());
//        bill.setShippingFee(BigDecimal.ZERO);
//        bill.setTotal(BigDecimal.ZERO);
//        bill.setOrderStatus(1);
//        bill.setCreateDate(LocalDate.now());
//        bill.setUpdateDate(LocalDate.now());
//        bill.setAccountId(cart.getAccountId());
//
//        List<OrderItem> billDetailList = new ArrayList<>();
//        for (CartItem item : cart.getCartItems()){
//            OrderItem billDetail = new OrderItem();
//            billDetail.setOrders(bill);
//            billDetail.setProductDetails(item.getProductDetails());
//            billDetail.setPrice(item.getPrice());
//            billDetail.setQuantity(item.getQuantity());
//            billDetail.setStatus("1");
//            orderItemRepository.save(billDetail);
//            billDetailList.add(billDetail);
//            ProductDetails productDetail = productDetailsRepository.findById(item.getProductDetails().getId()).orElse(null);
//            productDetail.setQuantity(productDetail.getQuantity() - item.getQuantity());
//            if (productDetail.getQuantity() == 0){
//                productDetail.setStatus(true);
//            }
//            productDetailsRepository.save(productDetail);
//        }
//
//        bill.setOrderItems(billDetailList);
//        cartService.deleteCartById(cart.getId());
//        return ordersRepository.save(bill);
//    }


//package com.example.datnsd56.service.impl;
//
//import com.example.datnsd56.entity.Cart;
//import com.example.datnsd56.entity.CartItem;
//import com.example.datnsd56.entity.OrderItem;
//import com.example.datnsd56.entity.Orders;
//import com.example.datnsd56.entity.ProductDetails;
//import com.example.datnsd56.repository.AccountRepository;
//import com.example.datnsd56.repository.OrderItemRepository;
//import com.example.datnsd56.repository.OrdersRepository;
//import com.example.datnsd56.repository.ProductDetailsRepository;
//import com.example.datnsd56.security.CustomerController;
//import com.example.datnsd56.security.UserInfoUserDetails;
//import com.example.datnsd56.service.CartService;
//import com.example.datnsd56.service.OrdersService;
//import com.fasterxml.jackson.core.type.TypeReference;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.stereotype.Service;
//
//import java.math.BigDecimal;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//import java.util.stream.Collectors;
//
//@Service
//public class OrdersServiceImpl implements OrdersService {
//    @Autowired
//    private OrdersRepository ordersRepository;
//    @Autowired
//    private OrderItemRepository orderItemRepository;
//
//    @Autowired
//    private UserInfoUserDetails userInfoUserDetails;
//
//    @Autowired
//    private ProductDetailsRepository productDetailsRepository;
//
//    @Autowired
//    private OrderItemRepository repository2;
//    @Autowired
//    private CartService cartService;
//    @Autowired
//    private AccountRepository accountRepository;
//
//    @Override
//    public Orders detailHD(Integer id) {
//        return ordersRepository.findById(id).orElse(null);
//    }
//
//    @Override
//    public List<Orders> getAll() {
//        return ordersRepository.findAllByOrderStatus(1);
//    }
//
//    @Override
//    public Page<Orders> getAllOrders(Integer page) {
//        Pageable pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "OrderDate"));
//        return ordersRepository.findAllByOrderStatusPT(pageable);
//    }
//
//    @Override
//    public void delete(Integer id) {
//        ordersRepository.deleteById(id);
//    }
//
//    @Override
//    public Orders update(Orders orders, Integer id) {
//        Optional<Orders> optional = ordersRepository.findById(id);
//        if (optional.isPresent()) {
//            Orders orders1 = optional.get();
//            orders.setId(orders1.getId());
//            orders.setOrderStatus(orders1.getOrderStatus());
//            orders.setCreateDate((orders1.getCreateDate()));
//            orders.setUpdateDate((new Date()));
//            return ordersRepository.save(orders);
//        }
//        return null;
//    }
//
//    @Override
//    public Orders planceOrder(Cart cart, String address) {
//        Orders orders = new Orders();
//        orders.setAccountId(cart.getAccountId());
//        orders.setAddress(address);
//        orders.setPhone(cart.getAccountId().getPhone());
//        orders.setShippingFee(BigDecimal.ZERO);
//        orders.setTotal(BigDecimal.ZERO);
//        orders.setCreateDate(new Date());
//        orders.setUpdateDate(new Date());
//        List<OrderItem> orderItemList = new ArrayList<>();
//        for (CartItem item : cart.getCartItems()) {
//            OrderItem orderItem = new OrderItem();
//            orderItem.setOrders(orders);
//            orderItem.setProductDetails(item.getProductDetails());
//            orderItem.setQuantity(item.getQuantity());
//            orderItem.setStatus("1");
//            orderItemRepository.save(orderItem);
//            orderItemList.add(orderItem);
//            ProductDetails productDetails = productDetailsRepository.findById(item.getProductDetails().getId()).orElse(null);
//            productDetails.setQuantity(productDetails.getQuantity() - item.getQuantity());
//            if (productDetails.getQuantity() == 0) {
//                productDetails.setStatus(true);
//            }
//            productDetailsRepository.save(productDetails);
//
//        }
//        orders.setOrderItems(orderItemList);
//        cartService.remove(cart.getId());
//        return ordersRepository.save(orders);
//    }
//
//    @Override
//    public Orders add(Orders hoaDon) {
//        hoaDon.setOrderStatus(1);
//        hoaDon.setCreateDate(new Date());
//        hoaDon.setUpdateDate(new Date());
//        return ordersRepository.save(hoaDon);
//    }
//
////    @Override
////    public Orders create(JsonNode orderDate) {
////        if (orderDate == null) {
////            throw new IllegalArgumentException("orderData cannot be null");
////        }
////
////        ObjectMapper mapper = new ObjectMapper();
////        Orders order = mapper.convertValue(orderDate, Orders.class);
////        order.setAccountId(accountRepository.findByName(userInfoUserDetails.getName()).get());
////        Orders savedOrder = ordersRepository.save(order);
////
////        JsonNode orderDetailsNode = orderDate.get("orderItem");
////        if (orderDetailsNode != null && orderDetailsNode.isArray()) {
////            TypeReference<List<OrderItem>> type = new TypeReference<>() {
////            };
////            List<OrderItem> details = mapper.convertValue(orderDetailsNode, type)
////                    .stream().peek(d -> d.setOrderId(Integer.parseInt(String.valueOf(savedOrder)))).collect(Collectors.toList());
////            repository2.saveAll(details);
////        } else {
////            throw new IllegalArgumentException("orderDetails must be a non-null array");
////        }
////
////        return order;
////    }
//}
