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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {
    @Autowired
    private ProductDetailsRepository productDetailsRepository;

    @Override
    public Page<ProductDetails> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo,5);
        return productDetailsRepository.findAll(pageable);
    }

    @Override
    public List<ProductDetails> getAllCTSP() {
        return productDetailsRepository.findAll();
    }

    @Override
    public Page<ProductDetails> search( Integer quantity, BigDecimal sellPrice) {
        Pageable pageable = PageRequest.of(0,5);
        return productDetailsRepository.search(quantity,sellPrice,pageable);
    }

    @Override
    public ProductDetails add(ProductDetails productDetails) {
        productDetails.setCreateDate(LocalDate.now());
        productDetails.setUpdateDate(LocalDate.now());
        return productDetailsRepository.save(productDetails);
    }

    @Override
    public ProductDetails getById(Integer id) {
        return productDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
     productDetailsRepository.deletects(id);
    }

    @Override
    public void update(ProductDetails productDetails) {
        productDetails.setCreateDate(LocalDate.now());
        productDetails.setUpdateDate(LocalDate.now());
        productDetailsRepository.save(productDetails);
    }
}
