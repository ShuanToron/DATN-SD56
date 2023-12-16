
package com.example.datnsd56.service;

import com.example.datnsd56.entity.Address;
import com.example.datnsd56.entity.Cart;
import com.example.datnsd56.entity.Orders;
import com.example.datnsd56.entity.Transactions;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;

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

Optional<Orders> getOrderId(Integer id);
    Orders add(Orders hoaDon);

//    Orders create(JsonNode orderDate);
}
