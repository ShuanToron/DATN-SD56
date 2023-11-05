package com.example.datnsd56.service;

import com.example.datnsd56.entity.ProductDetails;
import com.example.datnsd56.entity.Products;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ProductDetailsService {
    Page<ProductDetails> getAll(Integer pageNo);

    List<ProductDetails> getAllCTSP();

    Page<ProductDetails> search(  BigDecimal sellPrice);

    ProductDetails add(ProductDetails productDetails, MultipartFile[] files) throws IOException, SQLException;

    ProductDetails getById(Integer id);
    Products getOneProdcut(Integer id);

    List<ProductDetails> listPending();
    void delete(Integer id);
    List<ProductDetails> getProductsByProductId(Integer productId);

    void update(ProductDetails productDetails, MultipartFile[] files) throws IOException, SQLException;
}
