package com.example.e_commerce.controller;

import com.example.e_commerce.dto.ProductDto;
import com.example.e_commerce.exception.AlreadyExistsException;
import com.example.e_commerce.exception.ResourceNotFound;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.response.ApiResponse;
import com.example.e_commerce.service.product.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody ProductDto productDto){
        try{
            return new ResponseEntity<>(productService.addProduct(productDto), HttpStatus.CREATED);
        }
        catch (AlreadyExistsException e){
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable int id){
        try{
            return new ResponseEntity<>(productService.getProductById(id), OK);
        }
        catch (ResourceNotFound e){
            return ResponseEntity.status(NO_CONTENT).body(new ApiResponse("No product with this id"));
        }
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProductById(@PathVariable int id){
        try{
            productService.deleteProductById(id);
            return new ResponseEntity<>(OK);
        }
        catch(ResourceNotFound e){
            return ResponseEntity.status(OK).body(new ApiResponse("Product not deleted"));
        }
    }
    @PreAuthorize("hasAnyRole('ADMIN')")
    @PostMapping("/update/{id}")
    public ResponseEntity<?> updateProductById(@RequestBody Product product,@PathVariable int id){
        try{
            return new ResponseEntity<>(productService.updateProduct(product,id), OK);
        }
        catch(ResourceNotFound e){
            return ResponseEntity.status(NOT_MODIFIED).body(new ApiResponse("Product not updated"));
        }
    }
    @GetMapping("/ByCategory/{categoryId}")
    public ResponseEntity<?> getProductByCategory(@PathVariable int categoryId){
        try{
            return new ResponseEntity<>(productService.getProductByCategory(categoryId),OK);
        }
        catch(ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No Product found in this category"));
        }
    }

    @GetMapping("/ByBrand/{name}")
    public ResponseEntity<?> getProductByBrand(@PathVariable String name){
        try{
            return new ResponseEntity<>(productService.getProductByBrand(name),OK);
        }
        catch(ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found for this brand"));
        }
    }

    @GetMapping("/ByBrandandCategory/{name}/{brand}")
    public ResponseEntity<?> getProductByBrand(@PathVariable String name,@PathVariable String brand){
        try{
            return new ResponseEntity<>(productService.getProductsByCategoryAndBrand(name,brand),OK);
        }
        catch(ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found for this brand and category"));
        }
    }

    @GetMapping("/ByProductName/{name}")
    public ResponseEntity<?> getProductByName(@PathVariable String name){
        try{
            return new ResponseEntity<>(productService.getProductsByName(name),OK);
        }
        catch(ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found by this name"));
        }
    }

    @GetMapping("/ByBrandAndName/{brand}/{name}")
    public ResponseEntity<?> getProductByBrandandName(@PathVariable String brand,@PathVariable String name){
        try{
            return new ResponseEntity<>(productService.getProductsByBrandAndName(brand,name),OK);
        }
        catch(ResourceNotFound e){
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("No product found by this brand and name"));
        }
    }
}
