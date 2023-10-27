package com.example.datnsd56.controller;

import com.example.datnsd56.entity.Image;
import com.example.datnsd56.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/api/image/")
public class ImageController {
    private static String UPLOAD_DIR = System.getProperty("user.home") + "/media/upload";
    @Autowired
    private ImageService imageService;

    @PostMapping("upload")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        //Tạo thư mục chứa ảnh nếu không tồn tại
        File uploadDir = new File(UPLOAD_DIR);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        //Lấy tên file và đuôi mở rộng của file
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);


        Image image = new Image();
        image.setUrl(file.getName());


        //Tạo file
        File serveFile = new File(UPLOAD_DIR + "/" + image.getId() + "." + extension);
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(serveFile));
        bos.write(file.getBytes());
        bos.close();

        imageService.saveImage(image);
        return ResponseEntity.ok(image);


    }

}
