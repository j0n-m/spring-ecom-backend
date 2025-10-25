package com.ecommerce.spring_ecommerce.service;


import com.ecommerce.spring_ecommerce.payload.ProductDTO;
import com.ecommerce.spring_ecommerce.payload.ProductResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductService {

    ProductDTO addProduct(ProductDTO product, Long categoryId);

    ProductResponse getAllProducts();

    ProductResponse getProductsByCategoryId(Long categoryId);

    ProductResponse getProductsByKeyword(String keyword);

    ProductDTO updateProduct(Long productId, ProductDTO product);

    ProductDTO deleteProductById(Long productId);

    ProductDTO updateProductImage(Long productId, MultipartFile image);
}
