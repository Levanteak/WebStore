package com.example.webstore.service;

import com.example.webstore.dto.ReviewsDTO;
import com.example.webstore.model.Product;
import com.example.webstore.model.Reviews;
import com.example.webstore.model.User;
import com.example.webstore.repository.ProductRepository;
import com.example.webstore.repository.ReviewsRepository;
import com.example.webstore.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReviewsService {

    @Autowired
    private ReviewsRepository reviewsRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;

    public List<ReviewsDTO> getAllReviews() {
        List<Reviews> reviews = reviewsRepository.findAll();
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ReviewsDTO> getReviewById(Long id) {
        return reviewsRepository.findById(id)
                .map(this::convertToDTO);
    }

    public List<ReviewsDTO> getReviewsByProductId(Long productId) {
        List<Reviews> reviews = reviewsRepository.findByProductProductId(productId);
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewsDTO> getReviewsByUserId(Long userId) {
        List<Reviews> reviews = reviewsRepository.findByUserUserId(userId);
        return reviews.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public ReviewsDTO saveReview(ReviewsDTO reviewDTO) {
        User user = userRepository.findById(reviewDTO.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + reviewDTO.getUserId()));

        Product product = productRepository.findById(reviewDTO.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found with id: " + reviewDTO.getProductId()));

        Reviews review = new Reviews();
        review.setUser(user);
        review.setProduct(product);
        review.setComment(reviewDTO.getComment());
        review.setRating(reviewDTO.getRating());
        review.setFeedbackDate(reviewDTO.getFeedbackDate());
        review.setAuthor(reviewDTO.getAuthor());

        Reviews savedReview = reviewsRepository.save(review);
        return convertToDTO(savedReview);
    }


    public void deleteReview(Long id) {
        reviewsRepository.deleteById(id);
    }

    public ReviewsDTO convertToDTO(Reviews review) {
        ReviewsDTO dto = new ReviewsDTO();
        dto.setFeedbackId(review.getFeedbackId());
        dto.setUserId(review.getUser().getUserId());
        dto.setUserName(review.getUser().getFirstname() + " " + review.getUser().getLastname());
        dto.setUserEmail(review.getUser().getEmail());
        dto.setProductId(review.getProduct().getProductId());
        dto.setProductName(review.getProduct().getName());
        dto.setProductCategory(review.getProduct().getCategory().getCategoryName());
        dto.setProductPrice(review.getProduct().getPrice());
        dto.setComment(review.getComment());
        dto.setRating(review.getRating());
        dto.setFeedbackDate(review.getFeedbackDate());
        dto.setAuthor(review.getAuthor());
        return dto;
    }
}
