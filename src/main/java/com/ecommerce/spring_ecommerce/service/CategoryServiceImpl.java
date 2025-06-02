package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You cannot specify a category ID in the " +
                    "payload");
        }
        validateCategoryProperties(category);

        categoryRepository.save(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = getCategoryById(categoryId);

        categoryRepository.delete(category);

        return "Category '" + category.getCategoryName() + "' deleted successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Category validCategory = validateCategoryProperties(getCategoryById(categoryId));
        validCategory.setCategoryName(category.getCategoryName());
        categoryRepository.save(validCategory);

        return validCategory;
    }

    @Override
    public Category validateCategoryProperties(Category category) {
//        if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name cannot be empty");
//        }
        return category;
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categoryRepository
                .findById(categoryId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category was not found"));
    }
}
