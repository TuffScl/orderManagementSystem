package com.petproject.ordermanagmentsystem.services;

import com.petproject.ordermanagmentsystem.models.Category;
import com.petproject.ordermanagmentsystem.models.Customer;
import com.petproject.ordermanagmentsystem.repositories.CategoryRepository;
import com.petproject.ordermanagmentsystem.utils.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public void createNewCategory(Category category){
        categoryRepository.save(category);
    }

    public Category getCategoryById(int id){
        return categoryRepository.findById(id).orElse(null);
    }

    public List<Category> getAllCategories(){
        return categoryRepository.findAll();
    }

    @Transactional
    public Category updateCategoryById(int id, Category category) {
        if(categoryRepository.existsById(id)){
            category.setId(id);
            return categoryRepository.save(category);
        }
        else throw new ResourceNotFoundException("Category with id "+ id + " does not exist!");
    }

    @Transactional
    public void deleteCategoryById(int id) {
        if(categoryRepository.existsById(id)){
            categoryRepository.deleteById(id);
        }
        else throw new ResourceNotFoundException("Category with id "+ id + " does not exist!");
    }
}
