//package com.example.datnsd56.repository;
//
//import com.example.datnsd56.entity.Orders;
//import com.example.datnsd56.responsi.OrdersCustomer;
//import org.aspectj.weaver.ast.Or;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import java.util.List;
//import java.util.UUID;
//
//@Repository
//public interface OrdersRepository extends JpaRepository<Orders, Integer> {
//    @Query(value = "SELECT HD.id, HD.code, HD.fullname, HD.create_date, SUM(HDCT.quantity) AS tong_so_luong,\n" +
//            "            SUM(HDCT.quantity * HDCT.price) as tong_tien, HD.order_status\n" +
//            "            FROM Orders HD\n" +
//            "            JOIN order_item HDCT ON HD.id = HDCT.order_id\n" +
//            "            GROUP BY HD.id, HD.code, HD.fullname, HD.create_date, HD.total, HD.order_status, HD.phone\n" +
//            "            ORDER BY HD.create_date DESC",nativeQuery = true)
//
//    public Page<OrdersCustomer> hienThiPageHD(Pageable pageable);
//
//    List<Orders> findAllByOrderStatus(Integer status);
//
//    Page<Orders> findAllByOrderStatusPT(Pageable pageable,Integer satus);
//
//    @Query(value = "select * from orders b where b.order_status = ?1 and b.account_id= ?1",nativeQuery = true)
//    List<Orders> getOrdes(@Param("status") Integer order_status,
//                          @Param("accountId") Integer accountId);
//
//
//    Page<Orders> findAllByOrderStatusPT(Pageable pageable);
//}
