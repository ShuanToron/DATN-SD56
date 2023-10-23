package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Size;
import com.example.datnsd56.repository.SizeRepository;
import com.example.datnsd56.service.SizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SizeServiceImpl implements SizeService {
    @Autowired
    private SizeRepository sizeRepository;

    @Override
    public Page<Size> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return sizeRepository.findAll(pageable);
    }

    @Override
    public List<Size> getAllSZ() {
        return sizeRepository.findAll();
    }

    @Override
    public void add(Size size) {
        size.setCreateDate(LocalDate.now());
        size.setUpdateDate(LocalDate.now());
        sizeRepository.save(size);
    }

    @Override
    public Size getById(Integer id) {
        return sizeRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        sizeRepository.deleteById(id);
    }

    @Override
    public void update(Size size) {
        size.setCreateDate(LocalDate.now());
        size.setUpdateDate(LocalDate.now());
        sizeRepository.save(size);
    }
}