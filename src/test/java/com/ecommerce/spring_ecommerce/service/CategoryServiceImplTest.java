package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.exceptions.APIException;
import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void getAllCategoriesReturnsListOfCategories() {
        List<Category> mockCategories = List.of(new Category(1L, "Electronics"), new Category(2L, "Books"));
        when(categoryRepository.findAll()).thenReturn(mockCategories);

        List<Category> result = categoryService.getAllCategories();

        assertEquals(mockCategories, result);
    }

    @Test
    void getAllCategoriesReturnsEmptyList() {
        when(categoryRepository.findAll()).thenReturn(Collections.emptyList());

        List<Category> result = categoryService.getAllCategories();

        assertTrue(result.isEmpty());
    }

    @Test
    void createCategorySuccessfullyCreatesCategory() {
        Category mockCategory = new Category(null, "Electronics");
        when(categoryRepository.findByCategoryName(mockCategory.getCategoryName())).thenReturn(null);

        categoryService.createCategory(mockCategory);
        verify(categoryRepository).save(mockCategory);
    }

    @Test
    void createCategoryThrowsExceptionIfNameExists() {
        Category mockCategory = new Category(null, "Electronics");
        when(categoryRepository.findByCategoryName("Electronics")).thenReturn(mockCategory);

        assertThrows(APIException.class, () -> categoryService.createCategory(mockCategory));
    }

}