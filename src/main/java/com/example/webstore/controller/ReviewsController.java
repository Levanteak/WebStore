package com.example.webstore.controller;

import com.example.webstore.dto.ReviewsDTO;
import com.example.webstore.service.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewsController {

    @Autowired
    private ReviewsService reviewsService;

    @GetMapping
    public ResponseEntity<List<ReviewsDTO>> getAllReviews() {
        List<ReviewsDTO> reviews = reviewsService.getAllReviews();
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ReviewsDTO> getReviewById(@PathVariable Long id) {
        return reviewsService.getReviewById(id)
                .map(review -> new ResponseEntity<>(review, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<ReviewsDTO>> getReviewsByProductId(@PathVariable Long productId) {
        List<ReviewsDTO> reviews = reviewsService.getReviewsByProductId(productId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<ReviewsDTO>> getReviewsByUserId(@PathVariable Long userId) {
        List<ReviewsDTO> reviews = reviewsService.getReviewsByUserId(userId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<ReviewsDTO> createReview(@RequestBody ReviewsDTO reviewDTO) {
        ReviewsDTO savedReview = reviewsService.saveReview(reviewDTO);
        return new ResponseEntity<>(savedReview, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewsService.deleteReview(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
