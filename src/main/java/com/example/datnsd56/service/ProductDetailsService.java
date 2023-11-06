package com.example.datnsd56.service;

import com.example.datnsd56.entity.ProductDetails;
import com.example.datnsd56.entity.Products;
import jakarta.validation.constraints.Min;
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
    List<ProductDetails> findProductDetailsBySellPrice(Integer sellprine);
    List<ProductDetails> getProductDetailsById(Integer id);

    List<ProductDetails> listPending();
    void delete(Integer id);
    List<ProductDetails> getProductsByProductId(Integer productId);
    ProductDetails findProductDetailsByColorIdAndSizeId(Integer color, Integer size,Integer productId);

    void update(ProductDetails productDetails, MultipartFile[] files) throws IOException, SQLException;

    List<ProductDetails> findProductDetailsByColorIdAndSizeIdAndAndProductId(Integer colorId,Integer sizeId,Integer d);
@Min(value = 1, message = "lon hon 0") BigDecimal getprice(String color, String size);
}
