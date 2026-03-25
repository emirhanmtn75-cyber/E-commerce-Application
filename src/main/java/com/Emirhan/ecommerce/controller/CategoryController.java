package com.Emirhan.ecommerce.controller;

import com.Emirhan.ecommerce.entity.Category;
import com.Emirhan.ecommerce.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping
    public Category createCategory(@RequestBody Category category) {
        return categoryService.createCategory(category);
    }

    @PostMapping("/bulk")
    public List<Category> createCategories(@RequestBody List<Category> categories) {
        return categoryService.createCategories(categories);
    }

    @GetMapping("/{id}")
    public Category findCategoryById(@PathVariable Long id) {
        return categoryService.findCategoryById(id);
    }

    @GetMapping
    public List<Category> findAllCategories() {
        return categoryService.findAllCategories();
    }

    @DeleteMapping("/{id}")
    public void deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
}
