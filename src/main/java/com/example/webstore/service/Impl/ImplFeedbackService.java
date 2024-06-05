package com.example.webstore.service.Impl;

import com.example.webstore.dto.FeedbackConvertDTO;
import com.example.webstore.dto.FeedbackDTO;
import com.example.webstore.model.Feedback;

import java.util.List;

public interface ImplFeedbackService {
    FeedbackConvertDTO addFeedback(FeedbackConvertDTO feedbackDTO);
    List<FeedbackConvertDTO> getAllFeedbacks();
    FeedbackConvertDTO getFeedbackById(Long feedbackId);
    void deleteFeedback(Long feedbackId);
}
