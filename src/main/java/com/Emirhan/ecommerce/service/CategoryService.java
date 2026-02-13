package com.Emirhan.ecommerce.service;

import com.Emirhan.ecommerce.entity.Category;
import com.Emirhan.ecommerce.repository.CategoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> findAllCategories() {
        return categoryRepository.findAll();
    }

    @Transactional
    public List<Category> createCategories(List<Category> categories){
        return categoryRepository.saveAll(categories);
    }


    public Category findCategoryById(Long id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found!"));
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}
