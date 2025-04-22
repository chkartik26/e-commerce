package com.example.e_commerce.service.image;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IProductImageService {

    List<String> getProductImage(int productId);
    String addProductImage(int productId, MultipartFile file);
    void deleteImage(int imageId);
}
