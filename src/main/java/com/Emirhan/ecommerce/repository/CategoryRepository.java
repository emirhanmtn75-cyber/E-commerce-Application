package com.Emirhan.ecommerce.repository;

import com.Emirhan.ecommerce.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

        Category findByName(String categoryName);
}
