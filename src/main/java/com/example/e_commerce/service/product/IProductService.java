package com.example.e_commerce.service.product;

import com.example.e_commerce.dto.ProductDto;
import com.example.e_commerce.model.Product;

import java.util.List;

public interface IProductService {

    Product addProduct(ProductDto productDto);
    Product getProductById(int id);
    void deleteProductById(int id);
    Product updateProduct(Product product,int id);
    List<Product> getAllProducts();
    List<Product> getProductByCategory(int id);
    List<Product> getProductByBrand(String name);
    List<Product> getProductsByCategoryAndBrand(String category,String brand);
    Product getProductsByName(String name);
    List<Product> getProductsByBrandAndName(String category, String name);
}
