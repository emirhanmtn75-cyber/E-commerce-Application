package com.Emirhan.ecommerce.repository;

import com.Emirhan.ecommerce.entity.Category;
import com.Emirhan.ecommerce.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

}
