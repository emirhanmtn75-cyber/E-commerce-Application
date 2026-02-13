package com.Emirhan.ecommerce.service;

import com.Emirhan.ecommerce.dto.request.CreateProductRequest;
import com.Emirhan.ecommerce.dto.response.ProductResponse;
import com.Emirhan.ecommerce.entity.Category;
import com.Emirhan.ecommerce.entity.Product;
import com.Emirhan.ecommerce.repository.CategoryRepository;
import com.Emirhan.ecommerce.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    public ProductResponse createProduct(CreateProductRequest request) {

        Category category = categoryRepository.findById(request.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());
        product.setCategory(category);

        Product savedProduct = productRepository.save(product);

        return mapToResponse(savedProduct);
    }

    public List<Product> createProducts(List<CreateProductRequest> requests) {
    List<Product> products = requests.stream()
            .map(request -> {
                Category category = categoryRepository.findById(request.getCategoryId()).orElseThrow(() -> new RuntimeException("Category not found"));
                Product product = new Product();
                product.setName(request.getName());
                product.setDescription(request.getDescription());
                product.setPrice(request.getPrice());
                product.setStock(request.getStock());
                product.setCategory(category);
                return product;
            }).toList();
    return productRepository.saveAll(products);
    }
    private ProductResponse mapToResponse(Product product) {
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setStock(product.getStock());
        response.setCategoryId(product.getCategory().getId());
        response.setCategoryName(product.getCategory().getName());
        return response;
    }



    public List<ProductResponse> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }


    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapToResponse(product);
    }


    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
