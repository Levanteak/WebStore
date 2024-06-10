package com.example.webstore.service;

import com.example.webstore.dto.FeedbackAfterPurchaseConvertDTO;
import com.example.webstore.model.Purchase;
import com.example.webstore.repository.FeedbackAfterPurchase;
import com.example.webstore.repository.PurchaseRepository;
import com.example.webstore.service.Impl.ImplFeedbackAfterPurchaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService implements ImplFeedbackAfterPurchaseService {

    @Autowired
    private FeedbackAfterPurchase feedbackAfterPurchase;

    @Autowired
    private PurchaseRepository purchaseRepository;

    public FeedbackAfterPurchaseConvertDTO addFeedback(FeedbackAfterPurchaseConvertDTO feedbackDTO) {
        Purchase purchase = purchaseRepository.findById(feedbackDTO.getPurchaseId())
                .orElseThrow(() -> new RuntimeException("Purchase not found"));

        com.example.webstore.model.FeedbackAfterPurchase feedbackAfterPurchase = new com.example.webstore.model.FeedbackAfterPurchase();
        feedbackAfterPurchase.setPurchase(purchase);
        feedbackAfterPurchase.setUser(purchase.getUser());
        feedbackAfterPurchase.setProduct(purchase.getProduct());
        feedbackAfterPurchase.setAuthor(feedbackDTO.getAuthor());
        feedbackAfterPurchase.setComment(feedbackDTO.getComment());
        feedbackAfterPurchase.setRating(feedbackDTO.getRating());
        feedbackAfterPurchase.setFeedbackDate(new Date());

        com.example.webstore.model.FeedbackAfterPurchase savedFeedbackAfterPurchase = this.feedbackAfterPurchase.save(feedbackAfterPurchase);
        return convertToDTO(savedFeedbackAfterPurchase);
    }

    public List<FeedbackAfterPurchaseConvertDTO> getAllFeedbacks() {
        return feedbackAfterPurchase.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public FeedbackAfterPurchaseConvertDTO getFeedbackById(Long feedbackId) {
        com.example.webstore.model.FeedbackAfterPurchase feedbackAfterPurchase = this.feedbackAfterPurchase.findById(feedbackId).orElse(null);
        return feedbackAfterPurchase != null ? convertToDTO(feedbackAfterPurchase) : null;
    }

    public void deleteFeedback(Long feedbackId) {
        feedbackAfterPurchase.deleteById(feedbackId);
    }

    private FeedbackAfterPurchaseConvertDTO convertToDTO(com.example.webstore.model.FeedbackAfterPurchase feedbackAfterPurchase) {
        FeedbackAfterPurchaseConvertDTO feedbackDTO = new FeedbackAfterPurchaseConvertDTO();
        feedbackDTO.setFeedbackId(feedbackAfterPurchase.getFeedbackId());
        feedbackDTO.setPurchaseId(feedbackAfterPurchase.getPurchase().getPurchaseId());

        FeedbackAfterPurchaseConvertDTO.ProductSummary productSummary = new FeedbackAfterPurchaseConvertDTO.ProductSummary();
        productSummary.setProductId(feedbackAfterPurchase.getProduct().getProductId());
        productSummary.setName(feedbackAfterPurchase.getProduct().getName());
        productSummary.setDescription(feedbackAfterPurchase.getProduct().getDescription());
        feedbackDTO.setProduct(productSummary);

        FeedbackAfterPurchaseConvertDTO.UserSummary userSummary = new FeedbackAfterPurchaseConvertDTO.UserSummary();
        userSummary.setUserId(feedbackAfterPurchase.getUser().getUserId());
        userSummary.setLogin(feedbackAfterPurchase.getUser().getLogin());
        userSummary.setEmail(feedbackAfterPurchase.getUser().getEmail());
        feedbackDTO.setUser(userSummary);

        feedbackDTO.setAuthor(feedbackAfterPurchase.getAuthor());
        feedbackDTO.setComment(feedbackAfterPurchase.getComment());
        feedbackDTO.setRating(feedbackAfterPurchase.getRating());
        feedbackDTO.setFeedbackDate(feedbackAfterPurchase.getFeedbackDate());
        return feedbackDTO;
    }
}
