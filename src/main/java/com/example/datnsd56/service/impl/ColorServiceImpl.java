package com.example.datnsd56.service.impl;

import com.example.datnsd56.model.Color;
import com.example.datnsd56.repository.ColorRepository;
import com.example.datnsd56.service.ColorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ColorServiceImpl implements ColorService {
    @Autowired
    private ColorRepository colorRepository;

    @Override
    public Page<Color> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return colorRepository.findAll(pageable);
    }

    @Override
    public void add(Color color) {
        colorRepository.save(color);

    }

    @Override
    public Color getById(Integer id) {

        return colorRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        colorRepository.deleteById(id);
    }

    @Override
    public void update(Color color) {
        colorRepository.save(color);
    }
}
