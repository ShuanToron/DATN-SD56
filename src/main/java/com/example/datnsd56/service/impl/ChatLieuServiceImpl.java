package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Material;
import com.example.datnsd56.repository.ChatLieuRepository;
import com.example.datnsd56.service.ChatLieuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class ChatLieuServiceImpl implements ChatLieuService {
    @Autowired
    private ChatLieuRepository repository;

    @Override
    public Material addChatLieu(Material material) {
        material.setCreateDate(LocalDate.now());
        material.setUpdateDate(LocalDate.now());
        return repository.save(material);
    }

    @Override
    public void removeChatLieu(Integer id) {
        Material material = repository.findById(id).orElse(null);
        repository.delete(material);
    }

    @Override
    public Page<Material> pageMaterial(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        Page<Material> page = repository.findAll(pageable);
        return page;
    }

    @Override
    public List<Material> getAllChatLieu() {
        return repository.findAll();
    }
}
