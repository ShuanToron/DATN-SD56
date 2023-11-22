package com.example.datnsd56.service;

import com.example.datnsd56.entity.Orders;
import com.fasterxml.jackson.databind.JsonNode;

public interface OrdersService {
    Orders detailHD(Integer id);

    void delete(Integer id);


    Orders add(Orders hoaDon);

    Orders create(JsonNode orderDate);
}
