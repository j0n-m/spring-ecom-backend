package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.model.Product;
import com.ecommerce.spring_ecommerce.payload.ProductDTO;
import com.ecommerce.spring_ecommerce.payload.ProductResponse;
import com.ecommerce.spring_ecommerce.repository.CategoryRepository;
import com.ecommerce.spring_ecommerce.repository.ProductRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDTO addProduct(ProductDTO product, Long categoryId) {
        Product productEntity = modelMapper.map(product, Product.class);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "Category Id", categoryId));

        if (productEntity.getId() != null) {
            productEntity.setId(null);
        }
        productEntity.setCategory(category);
        productEntity.setSpecialPrice(productEntity.getSpecialPrice());

        Product savedProduct = productRepository.save(productEntity);
        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts() {
        List<ProductDTO> products = productRepository
                .findAll()
                .stream()
                .map((product) -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(products);
        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategoryId(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "Category Id", categoryId));
        List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOs = products.stream().map(product ->
                modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword) {
        String kw = keyword.toLowerCase();

        List<Product> products = productRepository.findByKeyword(kw);
        List<ProductDTO> productDTOs = products.stream().map(product ->
                modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO product) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product", "Product Id", productId));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setImage(product.getImage());
        existingProduct.setQuantity(product.getQuantity());
        existingProduct.setPrice(product.getPrice());
        existingProduct.setDiscount(product.getDiscount());
        existingProduct.setSpecialPrice(existingProduct.getSpecialPrice());

        Product updatedProduct = productRepository.save(existingProduct);
        return modelMapper.map(updatedProduct, ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProductById(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product", "Product Id", productId));
        productRepository.delete(product);
        return modelMapper.map(product, ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) {
        return null;
    }
}
