package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.payload.CategoryDTO;
import com.ecommerce.spring_ecommerce.payload.CategoryResponse;

import java.util.List;

public interface CategoryService {
    CategoryResponse getAllCategories();

    CategoryDTO createCategory(CategoryDTO category);

    String deleteCategory(Long categoryId);

    Category updateCategory(Long categoryId, Category category);
    

    Category getCategoryById(Long categoryId);
}
