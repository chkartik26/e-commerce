package com.example.e_commerce.controller;

import com.example.e_commerce.response.ApiResponse;
import com.example.e_commerce.service.image.ProductImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/productImage")
public class ProductImageController {

    @Autowired
    ProductImageService productImageService;
    @PostMapping("/addImage/{productId}")
    public ResponseEntity<?> addingImage(@PathVariable int productId,@RequestParam() MultipartFile file){
        try{
            return new ResponseEntity<>(productImageService.addProductImage(productId,file), HttpStatus.OK);
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(new ApiResponse("Image not uploaded"));
        }
    }

    @GetMapping("/getImage/{productId}")
    public ResponseEntity<?> getImage(@PathVariable int productId){
        try{
            return new ResponseEntity<>(productImageService.getProductImage(productId),HttpStatus.OK);
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Images not found"));
        }
    }
    @DeleteMapping("/deleteImage/{imageId}")
    public ResponseEntity<?> deleteImage(@PathVariable int imageId){
        try{
            productImageService.deleteImage(imageId);
            return new ResponseEntity<>(HttpStatus.OK);
        }
        catch (RuntimeException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ApiResponse("Image not deleted"));
        }
    }
}
