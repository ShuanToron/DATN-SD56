package com.example.datnsd56.service;

import com.example.datnsd56.entity.Cart;
import com.example.datnsd56.entity.Orders;
import com.example.datnsd56.entity.Voucher;

import java.math.BigDecimal;

public interface OrderSeriveV2 {
    void applyVoucher(Orders order, String voucherCode);
    void processOrderDetails(Cart cart, Orders order);
    void reduceProductStock(Integer id, int quantity);
    Orders createOrder(Cart cart, String address);
    Orders placeOrder(Cart cart, String address, String voucherCode);
    BigDecimal calculateDiscountValue(Voucher voucher, BigDecimal total);
}
