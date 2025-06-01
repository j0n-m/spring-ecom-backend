package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.model.Category;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final List<Category> categories = new ArrayList<>();
    private Long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public void createCategory(Category category) {
//        if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
//            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name cannot be empty");
//        }
        validateCategoryProperties(category);

        category.setCategoryId(nextId++);
        categories.add(category);
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category =
                categories.stream()
                        .filter(c -> c.getCategoryId().equals(categoryId))
                        .findFirst()
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category was not found"));

        categories.remove(category);

        return "Category '" + category.getCategoryName() + "' deleted successfully";
    }

    @Override
    public Category updateCategory(Long categoryId, Category category) {
        Category validCategory = validateCategoryProperties(getCategoryById(categoryId));
        validCategory.setCategoryName(category.getCategoryName());

        return validCategory;
    }

    @Override
    public Category validateCategoryProperties(Category category) {
        if (category.getCategoryName() == null || category.getCategoryName().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Category name cannot be empty");
        }
        return category;
    }

    @Override
    public Category getCategoryById(Long categoryId) {
        return categories.stream()
                .filter(c -> c.getCategoryId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category was not found"));
    }
}
