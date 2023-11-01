package com.example.datnsd56.service;

import com.example.datnsd56.entity.ProductDetails;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface ProductDetailsService {
    Page<ProductDetails> getAll(Integer pageNo);

    List<ProductDetails> getAllCTSP();

    Page<ProductDetails> search(Integer quantity, BigDecimal sellPrice);

    ProductDetails add(ProductDetails productDetails, MultipartFile[] files )throws IOException, SQLException;

    ProductDetails getById(Integer id);

    void delete(Integer id);

    void update(ProductDetails productDetails, MultipartFile[] files)throws IOException, SQLException;
}
