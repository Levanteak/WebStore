package com.example.webstore.repository;

import com.example.webstore.model.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Long> {
    List<Reviews> findByProductProductId(Long productId);
    List<Reviews> findByUserUserId(Long userId);
}