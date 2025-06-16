package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.payload.CategoryDTO;
import com.ecommerce.spring_ecommerce.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories(Integer page, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO category);

    CategoryDTO deleteCategory(Long categoryId);

    CategoryDTO updateCategory(Long categoryId, CategoryDTO categoryDTO);


    Category getCategoryById(Long categoryId);
}
