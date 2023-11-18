package com.example.datnsd56.service.impl;

import com.example.datnsd56.entity.Image;
import com.example.datnsd56.entity.ProductDetails;
import com.example.datnsd56.entity.Products;
import com.example.datnsd56.repository.ImageRepository;
import com.example.datnsd56.repository.ProductDetailsRepository;
import com.example.datnsd56.repository.ProductsRepository;
import com.example.datnsd56.service.ProductDetailsService;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
//import java.math.Double;
//import java.math.Double;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ProductDetailsServiceImpl implements ProductDetailsService {
    @Autowired
    private ProductDetailsRepository productDetailsRepository;
    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Page<ProductDetails> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, 5);
        return productDetailsRepository.findAll(pageable);
    }

    @Override
    public List<ProductDetails> getAllCTSP() {
        return productDetailsRepository.findAll();
    }

    @Override
    public Page<ProductDetails> search( Double sellPrice) {
        Pageable pageable = PageRequest.of(0, 5);
        return productDetailsRepository.findProductDetailsBySellPrice( sellPrice, pageable);
    }

    @Override
    public ProductDetails add(ProductDetails productDetails, MultipartFile[] files) throws IOException, SQLException {
        return null;
    }

    @Override
    public ProductDetails getByIds(Integer id) {
        return productDetailsRepository.getByIds(id);

    }
//    @Override
//    public ProductDetails add(ProductDetails productDetails, MultipartFile[] files) throws IOException, SQLException {
//        productDetails.setCreateDate(LocalDate.now());
//        productDetails.setUpdateDate(LocalDate.now());
//        productDetailsRepository.save(productDetails);
//        for (MultipartFile file : files) {
//            Image anhSanPham = new Image();
//            byte[] bytes = file.getBytes();
//            Blob blob = new SerialBlob(bytes);
//            anhSanPham.setProductDetailId(productDetails);
//            anhSanPham.setUrl(blob);
//            imageRepository.save(anhSanPham);
//        }
//        return productDetails;
//    }

    @Override
    public ProductDetails getById(Integer id) {
        return productDetailsRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductDetails> listPending() {
        return productDetailsRepository.listPending();
    }

    @Override
    public Products getOneProdcut(Integer id) {
        return productsRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductDetails> findProductDetailsBySellPrice(Integer sellprine) {
        return null;
    }

    @Override
    public List<ProductDetails> getProductDetailsById(Integer id) {
        return productDetailsRepository.getProductDetailsById(id);
    }

    @Override
    public void delete(Integer id) {
        productDetailsRepository.deletects(id);
    }

    @Override
    public List<ProductDetails> getProductsByProductId(Integer productId) {
        return productDetailsRepository.getProductDetailsByProductId(productId);
    }

    @Override
    public ProductDetails getCart(Integer productId, Integer color, Integer size) {
        ProductDetails productDetails=productDetailsRepository.getCart(productId,color,size);
        return productDetails;
    }

    @Override
    public Double getPrice(Integer id, Integer colorId, Integer sizeId) {
        Double price = productDetailsRepository.getDetail(id, colorId, sizeId);
        return price;
    }

    @Override
    public void update(ProductDetails productDetails, MultipartFile[] files) throws IOException, SQLException {
        productDetails.setCreateDate(LocalDate.now());
        productDetails.setUpdateDate(LocalDate.now());
        productDetailsRepository.save(productDetails);

//        for (MultipartFile file : files) {
//            Image anhSanPham = imageRepository.getImageByProductId(productDetails.getId()).get(0);
//            byte[] bytes = file.getBytes();
//            Blob blob = new SerialBlob(bytes);
//            anhSanPham.setProductId(productDetails.getProductId());
//            anhSanPham.setUrl(blob);
//            imageRepository.save(anhSanPham);
//        }
    }

    @Override
    public List<ProductDetails> findProductDetailsByColorIdAndSizeIdAndAndProductId(Integer colorId, Integer sizeId, Integer id) {
        return productDetailsRepository.findProductDetailsByColorIdAndSizeIdAndAndProductId(colorId,sizeId);
    }

    @Override
    public Double getprice(String color, String size) {
        return null;
    }

//    @Override
//    public @Min(value = 1, message = "lon hon 0") Double getprice(String color, String size) {
//        @Min(value = 1, message = "lon hon 0") Double productDetails=  productDetailsRepository.getPrice(color,size);
//
//        return productDetails;
//    }

//    @Override
//    public List<ProductDetails> getProductsByProductId(Integer productId) {
//        return productDetailsRepository.findByProductId(productId);
//    }


}
