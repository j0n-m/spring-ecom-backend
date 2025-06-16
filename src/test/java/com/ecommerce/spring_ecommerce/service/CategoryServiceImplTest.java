package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.payload.CategoryDTO;
import com.ecommerce.spring_ecommerce.repository.CategoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;

    @Spy
    private ModelMapper modelMapper;

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

//    @Test
//    void getAllCategoriesReturnsListOfCategories() {
//        List<Category> mockCategories = List.of(new Category(1L, "Electronics"), new Category(2L, "Books"));
//        when(categoryRepository.findAll()).thenReturn(mockCategories);
//
//        CategoryResponse result = categoryService.getAllCategories();
//
//        assertEquals(2, result.getContent().size());
//    }
//
//    @Test
//    void getAllCategoriesReturnsEmptyList() {
//        when(categoryRepository.findAll()).thenReturn(List.of());
//
//        CategoryResponse result = categoryService.getAllCategories();
//
//        assertTrue(result.getContent().isEmpty());
//    }

    @Test
    void createCategorySuccessfullyCreatesCategory() {
        CategoryDTO mockCategoryDTO = new CategoryDTO(null, "Electronics");
        Category savedMockCategory = new Category(1L, "Electronics");

        when(categoryRepository.findByCategoryName(mockCategoryDTO.getCategoryName())).thenReturn(null);
        when(categoryRepository.save(Mockito.any())).thenReturn(savedMockCategory);

        CategoryDTO savedCategoryDTO = this.categoryService.createCategory(mockCategoryDTO);
        verify(categoryRepository).save(Mockito.any());
        assertEquals(savedMockCategory.getCategoryName(), savedCategoryDTO.getCategoryName());


    }

    @Test
    void createCategoryThrowsExceptionIfNameExists() {
        CategoryDTO mockCategoryDTO = new CategoryDTO(1L, "Electronics");
        Category mockCategory = new Category(1L, "Electronics");

        when(modelMapper.map(mockCategoryDTO, Category.class)).thenReturn(mockCategory);
        when(modelMapper.map(mockCategory, CategoryDTO.class)).thenReturn(mockCategoryDTO);
        when(categoryRepository.findByCategoryName(mockCategoryDTO.getCategoryName())).thenReturn(mockCategory);


        assertThrows(Exception.class, () -> categoryService.createCategory(mockCategoryDTO));
    }

}