package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.model.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();

    void createCategory(Category category);

    String deleteCategory(Long categoryId);

    Category updateCategory(Long categoryId, Category category);

    Category validateCategoryProperties(Category category);

    Category getCategoryById(Long categoryId);
}
