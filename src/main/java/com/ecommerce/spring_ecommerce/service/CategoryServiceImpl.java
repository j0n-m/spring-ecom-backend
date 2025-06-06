package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.exceptions.APIException;
import com.ecommerce.spring_ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    //private final List<Category> categories = new ArrayList<>();
    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void createCategory(Category category) {
        if (category.getCategoryId() != null) {
            category.setCategoryId(null);
        }
        //check if category name already exists
        Category existingCategory = categoryRepository.findByCategoryName(category.getCategoryName());
        if (existingCategory != null) {
            throw new APIException("Category with the name " + category.getCategoryName() + " already exists");
        }

        this.categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);

        categoryRepository.delete(category);

        return "Category '" + category.getCategoryName() + "' deleted successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Category validCategory = getCategoryById(categoryId);
        validCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(validCategory);

        return validCategory;
    }


    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
    }
}
