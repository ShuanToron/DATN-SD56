package com.example.datnsd56.service;

import com.example.datnsd56.model.Image;
import com.example.datnsd56.model.Material;
import org.springframework.data.domain.Page;

public interface ImageService {
    Page<Image> getAll(Integer page);

    void add(Image image );

    Image getById(Integer id);

    void delete(Integer id);

    void update(Image image);
}
