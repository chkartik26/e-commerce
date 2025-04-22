package com.example.e_commerce.service.category;

import com.example.e_commerce.exception.AlreadyExistsException;
import com.example.e_commerce.exception.ResourceNotFound;
import com.example.e_commerce.model.Category;
import com.example.e_commerce.repository.CategoryRepository;
import org.hibernate.annotations.SQLInsert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService implements ICategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Category getCategoryById(int id){
        return categoryRepository.findById(id)
                .orElseThrow(()->new ResourceNotFound("Category not found!"));
    }

    @Override
    public Category getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public List<Category> getAllCategories() {
        List<Category> categories = categoryRepository.findAll();

        return categories;
    }

    @Override
    public Category addCategory(Category category) {
        String name=category.getName();
        if(categoryRepository.existsByName(name)){
            throw new AlreadyExistsException(category.getName()+" already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Category category,int id) {
        Category oldCategory=categoryRepository.findById(id).orElseThrow(()->new ResourceNotFound("Category not found!"));
        oldCategory.setName(category.getName());
        return categoryRepository.save(oldCategory);
    }

    @Override
    public void deleteCategoryById(int id) {
        categoryRepository.findById(id).orElseThrow(()->new ResourceNotFound("Category not found!"));
        categoryRepository.deleteById(id);
    }
}
