package com.example.webstore.repository;

import com.example.webstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategoryCategoryId(Long categoryId);
    List<Product> findByUserUserId(Long userId);

    List<Product> findByUserUserIdAndCategoryCategoryId(Long userId, Long categoryId);



}
