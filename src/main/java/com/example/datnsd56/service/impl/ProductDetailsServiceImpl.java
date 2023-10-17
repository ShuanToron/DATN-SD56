package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.ProductDetails;
import com.example.datnsd56.repository.ProductDetailsRepository;
import com.example.datnsd56.service.ProductDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {
    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Override
    public Page<ProductDetails> getAll(Pageable pageable) {
        return productDetailsRepository.findAll(pageable);
    }

    @Override
    public void add(ProductDetails productDetails) {
      productDetailsRepository.save(productDetails);
    }

    @Override
    public ProductDetails getById(Integer id) {
        return productDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
     productDetailsRepository.deleteById(id);
    }

    @Override
    public void update(ProductDetails productDetails) {
     productDetailsRepository.save(productDetails);
    }
}
