package com.ecommerce.spring_ecommerce.service;

import com.ecommerce.spring_ecommerce.exceptions.APIException;
import com.ecommerce.spring_ecommerce.exceptions.ResourceNotFoundException;
import com.ecommerce.spring_ecommerce.model.Category;
import com.ecommerce.spring_ecommerce.model.Product;
import com.ecommerce.spring_ecommerce.payload.ProductDTO;
import com.ecommerce.spring_ecommerce.payload.ProductResponse;
import com.ecommerce.spring_ecommerce.repository.CategoryRepository;
import com.ecommerce.spring_ecommerce.repository.ProductRepository;
import com.ecommerce.spring_ecommerce.util.PageableFactory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PageableFactory pageableFactory;

    @Value("${project.image.products}")
    private String path = "";

    @Override
    public ProductDTO addProduct(ProductDTO product, Long categoryId) {
        Product productEntity = modelMapper.map(product, Product.class);
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "Category Id", categoryId));

        List<Product> productsInCategory = category.getProducts();
        for (Product p : productsInCategory) {
            if (p.getName().equalsIgnoreCase(productEntity.getName())) {
                throw new APIException("Product with name '" + productEntity.getName() + "' already exists in this category.");
            }
        }

        if (productEntity.getId() != null) {
            productEntity.setId(null);
        }
        productEntity.setCategory(category);
        productEntity.setSpecialPrice(productEntity.getSpecialPrice());


        Product savedProduct = productRepository.save(productEntity);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }

    @Override
    public ProductResponse getAllProducts(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageDetails = pageableFactory.of(pageNumber, pageSize, sortBy, sortOrder);
        Page<Product> pageProducts = productRepository.findAll(pageDetails);

        List<ProductDTO> products = pageProducts
                .stream()
                .map((product) -> modelMapper.map(product, ProductDTO.class))
                .toList();
        ProductResponse productResponse = new ProductResponse();

        productResponse.setContent(products);
        productResponse.setPage(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getNumberOfElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByCategoryId(Long categoryId, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        Pageable pageDetails = pageableFactory.of(pageNumber, pageSize, sortBy, sortOrder);

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Category", "Category Id", categoryId));

        Page<Product> pageProducts = productRepository.findByCategory(category, pageDetails);
        //List<Product> products = productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO> productDTOs = pageProducts.stream().map(product ->
                modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);

        productResponse.setContent(productDTOs);
        productResponse.setPage(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getNumberOfElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

        return productResponse;
    }

    @Override
    public ProductResponse getProductsByKeyword(String keyword, Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
        String kw = keyword.toLowerCase();

        Pageable pageDetails = pageableFactory.of(pageNumber, pageSize, sortBy, sortOrder);

        Page<Product> pageProducts = productRepository.findByKeyword(kw, pageDetails);
        List<ProductDTO> productDTOs = pageProducts.stream().map(product ->
                modelMapper.map(product, ProductDTO.class)).toList();

        ProductResponse productResponse = new ProductResponse();
        productResponse.setContent(productDTOs);
        productResponse.setPage(pageProducts.getNumber());
        productResponse.setPageSize(pageProducts.getSize());
        productResponse.setTotalElements(pageProducts.getNumberOfElements());
        productResponse.setTotalPages(pageProducts.getTotalPages());
        productResponse.setLastPage(pageProducts.isLast());

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
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product", "Product Id", productId));

        String fileName = fileService.uploadImage(path, image);
        fileService.deleteImageIfExists(path, product.getImage());

        product.setImage(fileName);
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductDTO.class);
    }


}
