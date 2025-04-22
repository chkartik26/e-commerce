package com.example.e_commerce.service.image;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.example.e_commerce.model.ProductImage;
import com.example.e_commerce.repository.ProductImageRepository;
import com.example.e_commerce.repository.ProductRepository;
import com.example.e_commerce.service.product.ProductService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ProductImageService implements IProductImageService{
    @Autowired
    private AmazonS3 client;

    @Value("${app.s3.bucket}")
    private String bucketName;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductImageRepository productImageRepository;
    @Override
    public List<String> getProductImage(int productId) {
        List<String> allImagesUrl=productImageRepository.findByProductId(productId);
        return allImagesUrl;
    }


    @Override
    public String addProductImage(int productId, MultipartFile image) {
        String actualFileName=image.getOriginalFilename();
        String fileName= UUID.randomUUID().toString()+actualFileName.substring(actualFileName.lastIndexOf("."));
        ObjectMetadata metaData=new ObjectMetadata();
        metaData.setContentLength(image.getSize());
        try{
            PutObjectResult putObjectResult=client.putObject(new PutObjectRequest(bucketName,fileName,image.getInputStream(),metaData));
//            Users user2=userRepo.findById(user.getId());
//            user2.setDpURL(this.preSignedUrl(fileName));
            ProductImage productImage=new ProductImage();
            productImage.setImageUrl(this.preSignedUrl(fileName));
            productImage.setProduct(productRepository.findById(productId).orElseThrow());
            productImageRepository.save(productImage);
            return "Image Uploaded";
        } catch (IOException e) {
            return "Image Not Uploaded";
        }

    }

    public String preSignedUrl(String fileName) {
        Date expirationDate=new Date();
        long time= expirationDate.getTime();
        int hour=2;
        time +=hour*60*60*1000;
        expirationDate.setTime(time);
        GeneratePresignedUrlRequest generatePresignedUrlRequest= new GeneratePresignedUrlRequest(bucketName,fileName)
                .withMethod(HttpMethod.GET)
                .withExpiration(expirationDate);
        URL url=client.generatePresignedUrl(generatePresignedUrlRequest);
        return url.toString();
    }

    @Transactional
    @Override
    public void deleteImage(int imageId) {
        productImageRepository.deleteById(imageId);
    }
}
