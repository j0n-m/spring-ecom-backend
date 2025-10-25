package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.model.Product;
import com.ecommerce.spring_ecommerce.payload.ProductDTO;
import com.ecommerce.spring_ecommerce.repository.CategoryRepository;
import com.ecommerce.spring_ecommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(Product product, Long categoryId) {
        product = modelMapper.map(product, Product.class);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "Category Id", categoryId));

        product.setCategory(category);

        double discountedPrice = product.getPrice() -
                (product.getPrice() * product.getDiscount() / 100);
        product.setSpecialPrice(discountedPrice);

        Product savedProduct = productRepository.save(product);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }
}
