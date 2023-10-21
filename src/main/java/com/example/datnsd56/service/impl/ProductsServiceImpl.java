package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Products;
import com.example.datnsd56.repository.ProductsRepository;
import com.example.datnsd56.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsRepository repository;
    @Override
    public Page<Products> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Products> page1 = repository.findAll(pageable);
        return page1;

    }

    @Override
    public List<Products> getAllPro() {
        return repository.findAll();
    }

    @Override
    public void add(Products products) {
        products.setCreateDate(LocalDate.now());
        products.setUpdateDate(LocalDate.now());

        repository.save(products);
    }

    @Override
    public Products getById(Integer id) {
        Products products = repository.findById(id).orElse(null);
        return products;
    }

    @Override
    public void delete(Integer id) {
        Products products = getById(id);
        repository.delete(products);
    }

    @Override
    public void update(Products products) {
        products.setCreateDate(LocalDate.now());
        products.setUpdateDate(LocalDate.now());


        repository.save(products);
    }
}
