package com.example.e_commerce.service.category;

import com.example.e_commerce.model.Category;

import java.util.List;
import java.util.Optional;

public interface ICategoryService {
    Category getCategoryById(int id);
    Category getCategoryByName(String name);
    List<Category> getAllCategories();
    Category addCategory(Category category);
    Category updateCategory(Category category,int id);
    void deleteCategoryById(int id);
}
