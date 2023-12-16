package com.example.datnsd56.service;

import com.example.datnsd56.entity.Transactions;

import java.util.List;
import java.util.Optional;

public interface TransactionService {
    void saveTransaction(Transactions transaction);
    Optional<Transactions> getTransactionsById(Integer id);
    Transactions getById(Integer id);
    List<Transactions> get();
//    Transactions findByOrderInfo(String orderInfo);


}
