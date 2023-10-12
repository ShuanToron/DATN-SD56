package com.example.datnsd56.service;

import com.example.datnsd56.entity.Customers;
import org.springframework.data.domain.Page;

public interface CustomersService {
    Page<Customers> getAll(Integer page);
    Customers detail(Integer id);
    void add(Customers customers);
    void update(Customers customers);
    void delete(Integer id);

}
