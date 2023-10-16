package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Material;
import com.example.datnsd56.repository.MaterialRepository;
import com.example.datnsd56.service.MaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class MaterialServiceImpl implements MaterialService {
    @Autowired
    private MaterialRepository materialRepository;

    @Override
    public Page<Material> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 5);
        return materialRepository.findAll(pageable);
    }

    @Override
    public void add(Material material) {
        materialRepository.save(material);
    }

    @Override
    public Material getById(Integer id) {
        return materialRepository.findById(id).orElse(null);
    }

    @Override
    public void delete(Integer id) {
        materialRepository.deleteById(id);

    }

    @Override
    public void update(Material material) {
        materialRepository.save(material);
    }
}
