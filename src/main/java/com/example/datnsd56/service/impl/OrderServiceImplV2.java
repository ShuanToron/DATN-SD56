package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.*;
import com.example.datnsd56.repository.*;
import com.example.datnsd56.security.UserInfoUserDetails;
import com.example.datnsd56.service.CartService;
import com.example.datnsd56.service.OrderSeriveV2;
import com.example.datnsd56.service.VoucherUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OrderServiceImplV2 implements OrderSeriveV2 {
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

    @Transactional
    @Override
    public Orders placeOrder(Cart cart, String address, String voucherCode) {
        // Tạo đơn hàng và lưu vào cơ sở dữ liệu
        Orders order = createOrder(cart, address);
        if (order == null) {
            return null;
        }

        // Xử lý chi tiết đơn hàng và giảm số lượng sản phẩm
        processOrderDetails(cart, order);

        // Áp dụng voucher nếu có
        applyVoucher(order, voucherCode);

        // Lưu đơn hàng vào cơ sở dữ liệu
        try {
            order = ordersRepository.save(order);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        return order;
    }

    @Transactional
@Override
 public    Orders createOrder(Cart cart, String address) {
        // Tạo mới đối tượng Orders và thiết lập thông tin cần thiết
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

        // Lưu đơn hàng vào cơ sở dữ liệu
        try {
            return ordersRepository.save(bill);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional
    @Override

    public void reduceProductStock(Integer id, int quantity) {
        Optional<ProductDetails> productDetailOptional = productDetailsRepository.findById(id);

        if (productDetailOptional.isPresent()) {
            ProductDetails productDetail = productDetailOptional.get();

            // Kiểm tra xem có đủ số lượng tồn kho để giảm không
            if (productDetail.getQuantity() >= quantity) {
                int newStock = productDetail.getQuantity() - quantity;
                productDetail.setQuantity(newStock);

                // Lưu thông tin chi tiết sản phẩm với số lượng tồn kho mới vào cơ sở dữ liệu
                productDetailsRepository.save(productDetail);
            } else {
                // Xử lý trường hợp không đủ số lượng tồn kho
                // Có thể báo lỗi, ném exception hoặc thực hiện xử lý khác tùy thuộc vào yêu cầu của ứng dụng
            }
        } else {
            // Xử lý trường hợp không tìm thấy chi tiết sản phẩm
            // Có thể báo lỗi, ném exception hoặc thực hiện xử lý khác tùy thuộc vào yêu cầu của ứng dụng
        }
    }

    @Transactional
    @Override

    public void processOrderDetails(Cart cart, Orders order) {
        // Xử lý chi tiết đơn hàng và giảm số lượng sản phẩm
        Set<CartItem> cartItems = cart.getCartItems();
        for (CartItem cartItem : cartItems) {
            OrderItem orderDetails = new OrderItem();
            orderDetails.setOrders(order);
            orderDetails.setProductDetails(cartItem.getProductDetails());
            orderDetails.setQuantity(cartItem.getQuantity());
            orderDetails.setPrice(cartItem.getProductDetails().getSellPrice());
            orderDetails.setStatus("1");

            // Lưu chi tiết đơn hàng vào cơ sở dữ liệu
            orderItemRepository.save(orderDetails);

            // Giảm số lượng sản phẩm trong kho
            reduceProductStock(cartItem.getProductDetails().getId(), cartItem.getQuantity());
        }
    }

    @Transactional
    @Override

    public void applyVoucher(Orders order, String voucherCode) {
        if (voucherCode != null && !voucherCode.isEmpty()) {
            Optional<Voucher> voucherOptional = voucherService.findByCode(voucherCode);
            if (voucherOptional.isPresent()) {
                Voucher voucher = voucherOptional.get();
                boolean canUseVoucher = voucherService.canUseVoucher(order.getAccountId(), voucher);

                if (canUseVoucher) {
                    BigDecimal discountValue = calculateDiscountValue(voucher, order.getTotal());
                    BigDecimal discountedTotal = order.getTotal().subtract(discountValue);
                    order.setTotal(discountedTotal);

                    VoucherUsage voucherUsage = new VoucherUsage();
                    voucherUsage.setAccount(order.getAccountId());
                    voucherUsage.setVoucher(voucher);
                    voucherUsage.setUsedDate(LocalDateTime.now());
                    voucherUsage.setIsUsed(true);

                    voucherUsageRepository.save(voucherUsage);
                    order.setVoucherId(voucher);
                } else {
                    // Xử lý thông báo hoặc tạo exception tùy thuộc vào yêu cầu của bạn
                }
            }
        }
    }

    @Transactional
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
}
