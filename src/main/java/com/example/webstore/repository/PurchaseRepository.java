package com.example.webstore.repository;

import com.example.webstore.model.Purchase;
import com.example.webstore.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    List<Product> findByUserUserId(Long userId);
}
