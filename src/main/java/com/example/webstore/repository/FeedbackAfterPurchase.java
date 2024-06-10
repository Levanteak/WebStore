package com.example.webstore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FeedbackAfterPurchase extends JpaRepository<com.example.webstore.model.FeedbackAfterPurchase, Long> {
}
