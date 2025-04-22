package com.example.e_commerce.controller;

import com.example.e_commerce.model.Category;
import com.example.e_commerce.service.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/ById/{id}")
    public ResponseEntity<?> getCategoryById(@PathVariable int id){
        return new ResponseEntity<>(categoryService.getCategoryById(id), HttpStatus.OK);
    }
    @GetMapping("/ByName/{name}")
    public ResponseEntity<?> getCategoryByName(@PathVariable String name){
        Category category=categoryService.getCategoryByName(name);
        if(category!=null) {
            return new ResponseEntity<>(categoryService.getCategoryByName(name), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/all")
    public List<Category> getAllCategory(){
        return categoryService.getAllCategories();
    }


    @PostMapping("/add")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> addCategory(@RequestBody Category category){
        return new ResponseEntity<>(categoryService.addCategory(category),HttpStatus.OK);
    }
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?> updateCategory(@PathVariable int id,@RequestBody Category category){
        return new ResponseEntity<>(categoryService.updateCategory(category,id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<?>deleteCategory(@PathVariable int id){
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok("Account id deleted successfully");
    }
}

