package com.Emirhan.ecommerce.controller;

import com.Emirhan.ecommerce.dto.request.CreateProductRequest;
import com.Emirhan.ecommerce.dto.response.ProductResponse;
import com.Emirhan.ecommerce.entity.Product;
import com.Emirhan.ecommerce.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductResponse createProduct(@RequestBody CreateProductRequest request) {
        return productService.createProduct(request);
    }
    @PostMapping("/bulk")
    public List<Product> createProducts(@RequestBody List<CreateProductRequest> requests) {
        return productService.createProducts(requests);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}
