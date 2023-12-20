
package com.example.datnsd56.service;

import com.example.datnsd56.entity.*;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OrdersService {
    Orders detailHD(Integer id);

    List<Orders> getAll();

    Page<Orders> getAllOrders(Integer page);
    Transactions placeOrder(Cart cart, String address, String paymentMethod);
    void delete(Integer id);

    Orders update(Orders orders,Integer id);

    Orders planceOrder(Cart cart, String address);
   List<Orders> getAllOrders1(Integer accountId);
    Orders applyVoucherToOrder(Orders order, Voucher voucher);
Optional<Orders> getOrderId(Integer id);
    Orders add(Orders hoaDon);
    Orders processOrder(Orders order, String voucherCode, Account account);
    Orders placeOrders(Cart cart, String address, String voucherCode);
    boolean applyVoucher(String username, String voucherCode);
     BigDecimal getNewTotalAfterApplyingVoucher(String username);
    BigDecimal calculateDiscountValue(Voucher voucher, BigDecimal total);
//    Orders create(JsonNode orderDate);
}
