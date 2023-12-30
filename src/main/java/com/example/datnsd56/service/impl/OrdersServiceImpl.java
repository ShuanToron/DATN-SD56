
package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.*;
import com.example.datnsd56.repository.*;
import com.example.datnsd56.security.CustomerController;
import com.example.datnsd56.security.UserInfoUserDetails;
import com.example.datnsd56.service.CartService;
import com.example.datnsd56.service.OrdersService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersServiceImpl implements OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private UserInfoUserDetails userInfoUserDetails;

    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private CartService cartService;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Orders detailHD(Integer id) {
        return ordersRepository.findById(id).orElse(null);
    }

    @Override
    public List<Orders> getAll() {
        return ordersRepository.findAllByOrderStatus(1);
    }

    @Override
    public Page<Orders> getAllOrders(Integer page) {
        Pageable pageable = PageRequest.of(page, 5,Sort.by(Sort.Direction.DESC, "createDate"));
        return ordersRepository.findAll(pageable);
    }

    @Override
    public Orders getOneBill(Integer id) {
        return ordersRepository.findById(id).orElse(null);
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

//    public Orders planceOrder(Cart cart, String address) {
//        Orders orders = new Orders();
//        orders.setAccountId(cart.getAccountId());
////        Address customerAddress = addressRepository.findById(Integer.valueOf(address)).orElse(null);
//        orders.setAddress(address);
//        orders.setPhone(cart.getAccountId().getPhone());
//        orders.setShippingFee(BigDecimal.ZERO);
//        orders.setTotal(BigDecimal.ZERO);
//        orders.setCreateDate(LocalDate.now());
//        orders.setUpdateDate(LocalDate.now());
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

    @Override
    public Orders planceOrder(Cart cart, String address) {
        Orders bill = new Orders();
//        Address customerAddress = addressRepository.findById(Integer.valueOf(address)).orElse(null);
////        bill.setAccountId(customerAddress.getAccount());
//        bill.setAddress(customerAddress.getStreetName() + ", "
//                + customerAddress.getProvince() + ", "
//                + customerAddress.getDistrict() + ", "
//                + customerAddress.getZipcode());
        bill.setAddress(address);
        bill.setPhone(cart.getAccountId().getPhone());
        bill.setEmail(cart.getAccountId().getEmail());
        bill.setFullname(cart.getAccountId().getName());
        bill.setShippingFee(BigDecimal.ZERO);
        bill.setTotal(cart.getTotalPrice());
        bill.setOrderStatus(10);
        bill.setCreateDate(LocalDate.now());
        bill.setUpdateDate(LocalDate.now());
        bill.setAccountId(cart.getAccountId());

        try {
            bill = ordersRepository.save(bill);
        } catch (Exception e) {
            // Xử lý lỗi nếu không thể lưu Orders
            return null;
        }
        List<OrderItem> billDetailList = new ArrayList<>();
        for (CartItem item : cart.getCartItems()) {
            OrderItem billDetail = new OrderItem();
            billDetail.setOrders(bill);
            billDetail.setProductDetails(item.getProductDetails());
            billDetail.setPrice(item.getPrice());
            billDetail.setQuantity(item.getQuantity());
            billDetail.setStatus(1);
            orderItemRepository.save(billDetail);
            billDetailList.add(billDetail);
            ProductDetails productDetail = productDetailsRepository.findById(item.getProductDetails().getId()).orElse(null);
            productDetail.setQuantity(productDetail.getQuantity() - item.getQuantity());
            if (productDetail.getQuantity() == 0) {
                productDetail.setStatus(true);
            }
            productDetailsRepository.save(productDetail);
        }

        bill.setOrderItems(billDetailList);
        cartService.deleteCartById(cart.getId());
        return ordersRepository.save(bill);
    }

    @Override
    public List<Orders> getNoConfirmOrders(Integer accountId) {
        return ordersRepository.getOrdes(1, accountId);
    }

    @Override
    public List<OrderItem> getLstDetailByOrderId(Integer id) {
        return orderItemRepository.findAllByOrdersId(id);
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

    @Override
    public Orders add(Orders hoaDon) {
        hoaDon.setOrderStatus(1);
        hoaDon.setCreateDate(LocalDate.now());
        hoaDon.setUpdateDate(LocalDate.now());
        return ordersRepository.save(hoaDon);
    }

    @Override
    public Orders shippingOrder(Integer id, BigDecimal shippingFee) {
        Orders bill = ordersRepository.findById(id).orElse(null);
        bill.setOrderStatus(2);
        bill.setShippingFee(shippingFee);
        bill.setTotal(bill.getTotal().add(shippingFee));
        return ordersRepository.save(bill);
    }

    @Override
    public Orders completeOrder(Integer id) {
        Orders bill = ordersRepository.findById(id).orElse(null);
            bill.setOrderStatus(1);
            bill.setCreateDate(LocalDate.now());
        return ordersRepository.save(bill);

    }

    @Override
    @Transactional
    public Orders cancelOrder(Integer id) {
        Orders bill = ordersRepository.findById(id).orElse(null);
        bill.setOrderStatus(0);
//        List<OrderItem> billDetailList = bill.getOrderItems();
//        for (OrderItem billDetail : billDetailList){
//            billDetail.setStatus(0);
//            orderItemRepository.save(billDetail);
//            ProductDetails productDetail = productDetailsRepository.findById(billDetail.getProductDetails().getId()).orElse(null);
//            productDetail.setQuantity(productDetail.getQuantity() + billDetail.getQuantity());
//            productDetail.setStatus(true);
//            productDetailsRepository.save(productDetail);
//        }
//        bill.setOrderItems(billDetailList);
        return ordersRepository.save(bill);
    }

    @Override
    public Orders acceptBill(Integer Id) {
        Orders bill = ordersRepository.findById(Id).orElse(null);
        bill.setOrderStatus(3);
//        bill.setCreateDate(LocalDate.now());
//        bill.setUpdateDate(LocalDate.now());
        return ordersRepository.save(bill);
    }


    @Override
    public List<Orders> getAllOrders1(Integer accountId) {
        return ordersRepository.getAllOrders(accountId);
    }
}


