package com.ecommerce.spring_ecommerce.controller;

import com.ecommerce.spring_ecommerce.payload.ProductDTO;
import com.ecommerce.spring_ecommerce.payload.ProductResponse;
import com.ecommerce.spring_ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/public/products")
    public ResponseEntity<ProductResponse> getAllProducts() {
        ProductResponse productResponse = productService.getAllProducts();
        return ResponseEntity.ok(productResponse);
    }

    @PostMapping("/admin/categories/{categoryId}/products")
    public ResponseEntity<ProductDTO> addProduct(@RequestBody ProductDTO product, @PathVariable Long categoryId) {
        ProductDTO productDTO = productService.addProduct(product, categoryId);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping("/public/categories/{categoryId}/products")
    public ResponseEntity<ProductResponse> getProductsByCategory(@PathVariable Long categoryId) {
        ProductResponse productResponse = productService.getProductsByCategoryId(categoryId);
        return ResponseEntity.ok(productResponse);
    }

    @GetMapping("/public/products/keyword/{keyword}")
    public ResponseEntity<ProductResponse> getProductsByKeyword(@PathVariable String keyword) {
        ProductResponse productResponse = productService.getProductsByKeyword(keyword);
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
                                                         @RequestParam("Image") MultipartFile image) {
        ProductDTO productDTO = productService.updateProductImage(productId, image);
        return ResponseEntity.ok(productDTO);
    }
}
