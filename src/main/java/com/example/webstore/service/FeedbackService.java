package com.example.webstore.service;

import com.example.webstore.dto.FeedbackConvertDTO;
import com.example.webstore.model.Feedback;
import com.example.webstore.model.Purchase;
import com.example.webstore.repository.FeedbackRepository;
import com.example.webstore.repository.PurchaseRepository;
import com.example.webstore.service.Impl.ImplFeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService implements ImplFeedbackService {

    @Autowired
    private FeedbackRepository feedbackRepository;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public FeedbackConvertDTO addFeedback(FeedbackConvertDTO feedbackDTO) {
        Purchase purchase = purchaseRepository.findById(feedbackDTO.getPurchaseId())
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        Feedback feedback = new Feedback();
        feedback.setPurchase(purchase);
        feedback.setUser(purchase.getUser());
        feedback.setProduct(purchase.getProduct());
        feedback.setAuthor(feedbackDTO.getAuthor());
        feedback.setComment(feedbackDTO.getComment());
        feedback.setRating(feedbackDTO.getRating());
        feedback.setFeedbackDate(new Date());

        Feedback savedFeedback = feedbackRepository.save(feedback);
        return convertToDTO(savedFeedback);
    }

    public List<FeedbackConvertDTO> getAllFeedbacks() {
        return feedbackRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FeedbackConvertDTO getFeedbackById(Long feedbackId) {
        Feedback feedback = feedbackRepository.findById(feedbackId).orElse(null);
        return feedback != null ? convertToDTO(feedback) : null;
    }

    public void deleteFeedback(Long feedbackId) {
        feedbackRepository.deleteById(feedbackId);
    }

    private FeedbackConvertDTO convertToDTO(Feedback feedback) {
        FeedbackConvertDTO feedbackDTO = new FeedbackConvertDTO();
        feedbackDTO.setFeedbackId(feedback.getFeedbackId());
        feedbackDTO.setPurchaseId(feedback.getPurchase().getPurchaseId());

        FeedbackConvertDTO.ProductSummary productSummary = new FeedbackConvertDTO.ProductSummary();
        productSummary.setProductId(feedback.getProduct().getProductId());
        productSummary.setName(feedback.getProduct().getName());
        productSummary.setDescription(feedback.getProduct().getDescription());
        feedbackDTO.setProduct(productSummary);

        FeedbackConvertDTO.UserSummary userSummary = new FeedbackConvertDTO.UserSummary();
        userSummary.setUserId(feedback.getUser().getUserId());
        userSummary.setLogin(feedback.getUser().getLogin());
        userSummary.setEmail(feedback.getUser().getEmail());
        feedbackDTO.setUser(userSummary);

        feedbackDTO.setAuthor(feedback.getAuthor());
        feedbackDTO.setComment(feedback.getComment());
        feedbackDTO.setRating(feedback.getRating());
        feedbackDTO.setFeedbackDate(feedback.getFeedbackDate());
        return feedbackDTO;
    }
}
