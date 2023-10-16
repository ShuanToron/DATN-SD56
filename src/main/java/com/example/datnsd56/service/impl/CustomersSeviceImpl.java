package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Customers;
import com.example.datnsd56.repository.CustomersRepository;
import com.example.datnsd56.service.CustomersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CustomersSeviceImpl implements CustomersService {
    @Autowired
    private CustomersRepository customersRepository;

    @Override
    public Page<Customers> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return customersRepository.findAll(pageable);
    }

    @Override
    public Customers detail(Integer id) {
        Customers customers = customersRepository.findById(id).get();
        return customers;
    }

    @Override
    public void add(Customers customers) {
        customersRepository.save(customers);
    }

    @Override
    public void update(Customers customers) {
        customersRepository.save(customers);

    }

    @Override
    public void delete(Integer id) {
        Customers customers = detail(id);
        customersRepository.delete(customers);
    }
}
