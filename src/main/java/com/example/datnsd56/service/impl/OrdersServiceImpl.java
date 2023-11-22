package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.OrderItem;
import com.example.datnsd56.entity.Orders;
import com.example.datnsd56.repository.AccountRepository;
import com.example.datnsd56.repository.OrderItemRepository;
import com.example.datnsd56.repository.OrdersRepository;
import com.example.datnsd56.security.CustomerController;
import com.example.datnsd56.security.UserInfoUserDetails;
import com.example.datnsd56.service.OrdersService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private UserInfoUserDetails userInfoUserDetails;

    @Autowired
    private OrderItemRepository repository2;

    @Autowired
    private AccountRepository accountRepository;
    @Override
    public Orders detailHD(Integer id) {
        return ordersRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
      ordersRepository.deleteById(id);
    }

    @Override
    public Orders add(Orders hoaDon) {
        return ordersRepository.save(hoaDon);
    }

    @Override
    public Orders create(JsonNode orderDate) {
        if (orderDate == null) {
            throw new IllegalArgumentException("orderData cannot be null");
        }

        ObjectMapper mapper = new ObjectMapper();
        Orders order = mapper.convertValue(orderDate, Orders.class);
        order.setAccountId(accountRepository.findByName(userInfoUserDetails.getName()).get());
        Orders savedOrder = ordersRepository.save(order);

        JsonNode orderDetailsNode = orderDate.get("orderItem");
        if (orderDetailsNode != null && orderDetailsNode.isArray()) {
            TypeReference<List<OrderItem>> type = new TypeReference<>() {};
            List<OrderItem> details = mapper.convertValue(orderDetailsNode, type)
                    .stream().peek(d -> d.setOrderId(Integer.parseInt(String.valueOf(savedOrder)))).collect(Collectors.toList());
            repository2.saveAll(details);
        } else {
            throw new IllegalArgumentException("orderDetails must be a non-null array");
        }

        return order;
    }
}
