package com.example.datnsd56.service.impl;

import com.example.datnsd56.model.ShoeSole;
import com.example.datnsd56.repository.ShoeSoleRepository;
import com.example.datnsd56.service.ShoeSoleSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ShoeSoleServiceImpl implements ShoeSoleSevice {
    @Autowired
    private ShoeSoleRepository shoeSoleRepository;

    @Override
    public Page<ShoeSole> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return shoeSoleRepository.findAll(pageable);
    }

    @Override
    public void add(ShoeSole shoeSole) {
        shoeSoleRepository.save(shoeSole);
    }

    @Override
    public ShoeSole getById(Integer id) {
        return shoeSoleRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        shoeSoleRepository.deleteById(id);
    }

    @Override
    public void update(ShoeSole shoeSole) {
        shoeSoleRepository.save(shoeSole);
    }
}
