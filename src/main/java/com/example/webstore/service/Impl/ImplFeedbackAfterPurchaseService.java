package com.example.webstore.service.Impl;

import com.example.webstore.dto.FeedbackAfterPurchaseConvertDTO;

import java.util.List;

public interface ImplFeedbackAfterPurchaseService {
    FeedbackAfterPurchaseConvertDTO addFeedback(FeedbackAfterPurchaseConvertDTO feedbackDTO);
    List<FeedbackAfterPurchaseConvertDTO> getAllFeedbacks();
    FeedbackAfterPurchaseConvertDTO getFeedbackById(Long feedbackId);
    void deleteFeedback(Long feedbackId);
}
