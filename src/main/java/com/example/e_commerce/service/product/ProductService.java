package com.example.e_commerce.service.product;

import com.example.e_commerce.dto.ProductDto;
import com.example.e_commerce.exception.AlreadyExistsException;
import com.example.e_commerce.exception.ResourceNotFound;
import com.example.e_commerce.model.Category;
import com.example.e_commerce.model.Product;
import com.example.e_commerce.repository.CategoryRepository;
import com.example.e_commerce.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements IProductService {

    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product addProduct(ProductDto productDto) {
        Product product=new Product();
        product.setName(productDto.getName());
        product.setBrand(productDto.getBrand());
        product.setPrice(productDto.getPrice());
        product.setInventory(productDto.getInventory());
        product.setDescription(productDto.getDescription());

        if(productRepository.existsByName(product.getName())){
            throw new AlreadyExistsException(product.getName()+" already exists");
        }
        if(!categoryRepository.existsById(productDto.getCategoryId())){
            throw new IllegalArgumentException("Category must already be saved and have an ID.");
        }
        Category category=categoryRepository.findById(productDto.getCategoryId()).orElseThrow(()->new ResourceNotFound("No product found with this id"));
        if(category==null){
            throw new IllegalArgumentException("Category must already be saved and have an ID.");
        }
        product.setCategory(category);
//        if (product.getCategory() != null && product.getCategory().getId() == null) {
//            categoryRepository.save(product.getCategory());
//        }

       // categoryRepository.findByName(.getName())
        //Category category =product.getCategory();
//        if (category == null ) {
//            throw new IllegalArgumentException("Category must already be saved and have an ID.");
////            categoryRepository.save(category);
//        }
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(int id) {
        return productRepository.findById(id).orElseThrow(()->new ResourceNotFound("No product found with this id"));
    }

    @Override
    public void deleteProductById(int id) {
        productRepository.findById(id).ifPresentOrElse(product->{productRepository.deleteById(id);},()->{throw new ResourceNotFound("No product found with this id");});
    }

    @Override
    public Product updateProduct(Product product, int id) {
        Product updatedProduct=productRepository.findById(id).orElseThrow(()->new ResourceNotFound("No product found with this id"));
        if(product.getName()!=null){
            updatedProduct.setName(product.getName());
        }
        if(product.getBrand()!=null){
            updatedProduct.setBrand(product.getBrand());
        }
        if(product.getPrice()!=0){
            updatedProduct.setPrice(product.getPrice());
        }
        if(product.getInventory()!=0){
            updatedProduct.setInventory(product.getInventory());
        }
        if(product.getDescription()!=null){
            updatedProduct.setDescription(product.getDescription());
        }

        return productRepository.save(updatedProduct);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductByCategory(int categoryId) {
        List<Product> productbycategory=productRepository.findByCategoryId(categoryId);
        if(productbycategory.isEmpty()){
            throw new ResourceNotFound("No product found by this category");
        }
        return productbycategory;
    }


    @Override
    public List<Product> getProductByBrand(String name) {
        List<Product> productbybrand=productRepository.findByBrand(name);
        if(productbybrand.isEmpty()){
            throw new ResourceNotFound("No product found by this brand name");
        }
        return productbybrand;
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(String category, String brand) {
        Category category1=categoryRepository.findByName(category);
        int id=category1.getId();
        List<Product> productbybrandandcategory=productRepository.findByBrandAndCategoryId(brand,id);
        if(productbybrandandcategory.isEmpty()){
            throw new ResourceNotFound("No product found by this brand name and category");
        }
        return productbybrandandcategory;
    }

    @Override
    public Product getProductsByName(String name) {
        Product product=productRepository.findByName(name);
        if(product==null){
            throw new ResourceNotFound("No product found by this name");
        }
        return product;
    }

    @Override
    public List<Product> getProductsByBrandAndName(String brand, String name) {
        List<Product> productsbybrandandname=productRepository.findByBrandAndName(brand,name);
        if(productsbybrandandname.isEmpty()){
            throw new ResourceNotFound("No product found by this brand and name");
        }
        return productsbybrandandname;
    }


}
