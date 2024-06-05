package com.example.webstore.repository;

import com.example.webstore.model.Basket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BasketRepository extends JpaRepository<Basket, Long> {
    List<Basket> findByBasketId(Long basketId);
    List<Basket> findByUserUserId(Long userId);
}
