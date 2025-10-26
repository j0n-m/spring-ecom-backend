package com.ecommerce.spring_ecommerce.controller;

import com.ecommerce.spring_ecommerce.config.AppConstants;
import com.ecommerce.spring_ecommerce.payload.ProductDTO;
import com.ecommerce.spring_ecommerce.payload.ProductResponse;
import com.ecommerce.spring_ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts(
            @RequestParam(name = "page", required = false, defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = AppConstants.SORT_DIRECTION) String sortOrder
    ) {
        ProductResponse productResponse = productService.getAllProducts(pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping("/admin/categories/{categoryId}/products")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO product, @PathVariable Long categoryId) {
        ProductDTO productDTO = productService.addProduct(product, categoryId);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(
            @PathVariable Long categoryId,
            @RequestParam(name = "page", required = false, defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = AppConstants.SORT_DIRECTION) String sortOrder
    ) {
        ProductResponse productResponse = productService.getProductsByCategoryId(categoryId, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(
            @PathVariable String keyword,
            @RequestParam(name = "page", required = false, defaultValue = AppConstants.PAGE_NUMBER) Integer pageNumber,
            @RequestParam(name = "pageSize", required = false, defaultValue = AppConstants.PAGE_SIZE) Integer pageSize,
            @RequestParam(name = "sortBy", required = false, defaultValue = AppConstants.SORT_PRODUCTS_BY) String sortBy,
            @RequestParam(name = "sortOrder", required = false, defaultValue = AppConstants.SORT_DIRECTION) String sortOrder
    ) {
        ProductResponse productResponse = productService.getProductsByKeyword(keyword, pageNumber, pageSize, sortBy, sortOrder);
        return ResponseEntity.ok(productResponse);
    }

    @PutMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long productId, @RequestBody ProductDTO product) {
        ProductDTO productDTO = productService.updateProduct(productId, product);
        return ResponseEntity.ok(productDTO);
    }

    @DeleteMapping("/admin/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProductById(@PathVariable Long productId) {
        ProductDTO productDTO = productService.deleteProductById(productId);
        return ResponseEntity.ok(productDTO);
    }

    @PutMapping("/admin/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(@PathVariable Long productId,
                                                         @RequestParam("image") MultipartFile image) throws IOException {
        ProductDTO productDTO = productService.updateProductImage(productId, image);
        return ResponseEntity.ok(productDTO);
    }
}
