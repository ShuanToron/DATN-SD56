package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Products;
import com.example.datnsd56.repository.ProductsRepository;
import com.example.datnsd56.service.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductsServiceImpl implements ProductsService {
    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public Page<Products> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return productsRepository.findAll(pageable);
    }

    @Override
    public List<Products> getAllSP() {
        return productsRepository.findAll();
    }

    @Override
    public void add(Products products) {
        productsRepository.save(products);
    }

    @Override
    public Products getById(Integer id) {
        return productsRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        productsRepository.deleteById(id);
    }

    @Override
    public void update(Products products) {

    }
}
