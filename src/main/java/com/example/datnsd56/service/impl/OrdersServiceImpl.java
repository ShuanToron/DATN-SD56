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
