package com.example.webstore.controller;

import com.example.webstore.dto.FeedbackAfterPurchaseConvertDTO;
import com.example.webstore.service.FeedbackService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedbacks")
@CrossOrigin("http://localhost:3000")
@Tag(name = "FeedbackAfterPurchase Controller", description = "API для управления отзывами")
public class FeedbackAfterPurchaseController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/add")
    public ResponseEntity<FeedbackAfterPurchaseConvertDTO> addFeedback(@RequestBody FeedbackAfterPurchaseConvertDTO feedbackDTO) {
        FeedbackAfterPurchaseConvertDTO createdFeedback = feedbackService.addFeedback(feedbackDTO);
        return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FeedbackAfterPurchaseConvertDTO>> getAllFeedbacks() {
        List<FeedbackAfterPurchaseConvertDTO> feedbacks = feedbackService.getAllFeedbacks();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/get/{feedbackId}")
    public ResponseEntity<FeedbackAfterPurchaseConvertDTO> getFeedbackById(@PathVariable Long feedbackId) {
        FeedbackAfterPurchaseConvertDTO feedback = feedbackService.getFeedbackById(feedbackId);
        return feedback != null ? new ResponseEntity<>(feedback, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
