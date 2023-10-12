package com.example.datnsd56.service.impl;

import com.example.datnsd56.model.Brand;
import com.example.datnsd56.repository.BrandRepository;
import com.example.datnsd56.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Page<Brand> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return brandRepository.findAll(pageable);
    }

    @Override
    public void add(Brand brand) {
        brandRepository.save(brand);
    }

    @Override
    public Brand getById(Integer id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        brandRepository.deleteById(id);
    }

    @Override
    public void update(Brand brand) {
        brandRepository.save(brand);
    }
}
