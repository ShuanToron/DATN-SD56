package com.example.datnsd56.repository;

import com.example.datnsd56.entity.Transactions;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TransactionsRepository extends JpaRepository<Transactions,Integer> {
    Optional<Transactions> getTransactionsById(Integer id);
    Transactions findByOrderInfo(String orderInfo);
}
