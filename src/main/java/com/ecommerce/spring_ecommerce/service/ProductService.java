package com.ecommerce.spring_ecommerce.service;


import com.ecommerce.spring_ecommerce.model.Product;
import com.ecommerce.spring_ecommerce.payload.ProductDTO;

public interface ProductService {

    ProductDTO addProduct(Product product, Long categoryId);
}
