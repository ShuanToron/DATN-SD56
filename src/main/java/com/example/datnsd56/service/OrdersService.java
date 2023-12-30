
package com.example.datnsd56.service;

import com.example.datnsd56.entity.Cart;
import com.example.datnsd56.entity.OrderItem;
import com.example.datnsd56.entity.Orders;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.util.List;

public interface OrdersService {
    Orders detailHD(Integer id);

    List<Orders> getAll();

    Page<Orders> getAllOrders(Integer page);

    Orders getOneBill(Integer id);

    void delete(Integer id);

    Orders update(Orders orders, Integer id);

    Orders planceOrder(Cart cart, String address);

    List<Orders> getNoConfirmOrders(Integer accountId);

    List<OrderItem> getLstDetailByOrderId(Integer id);

    List<Orders> getAllOrders1(Integer accountId);

    Orders cancelOrder(Integer Id);

    Orders acceptBill(Integer Id);

    Orders add(Orders hoaDon);

    Orders shippingOrder(Integer id, BigDecimal shippingFee);

    Orders completeOrder(Integer id);

//    Orders create(JsonNode orderDate);
}
