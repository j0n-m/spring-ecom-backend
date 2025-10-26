package com.ecommerce.spring_ecommerce.service;


import com.ecommerce.spring_ecommerce.payload.ProductDTO;
import com.ecommerce.spring_ecommerce.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    ProductDTO addProduct(ProductDTO product, Long categoryId);

    ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getProductsByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductResponse getProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    ProductDTO updateProduct(Long productId, ProductDTO product);

    ProductDTO deleteProductById(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;
}
