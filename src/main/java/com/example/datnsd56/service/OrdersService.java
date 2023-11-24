package com.example.datnsd56.service;

import com.example.datnsd56.entity.Cart;
import com.example.datnsd56.entity.Orders;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrdersService {
    Orders detailHD(Integer id);

    List<Orders> getAll();

    Page<Orders> getAllOrders(Integer page);

    void delete(Integer id);

    Orders update(Orders orders,Integer id);

    Orders planceOrder(Cart cart,String address);



    Orders add(Orders hoaDon);

//    Orders create(JsonNode orderDate);
}
