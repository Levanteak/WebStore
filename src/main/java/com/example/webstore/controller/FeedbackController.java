package com.example.webstore.controller;

import com.example.webstore.dto.FeedbackConvertDTO;
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
@Tag(name = "Feedback Controller", description = "API для управления отзывами")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/add")
    public ResponseEntity<FeedbackConvertDTO> addFeedback(@RequestBody FeedbackConvertDTO feedbackDTO) {
        FeedbackConvertDTO createdFeedback = feedbackService.addFeedback(feedbackDTO);
        return new ResponseEntity<>(createdFeedback, HttpStatus.CREATED);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FeedbackConvertDTO>> getAllFeedbacks() {
        List<FeedbackConvertDTO> feedbacks = feedbackService.getAllFeedbacks();
        return new ResponseEntity<>(feedbacks, HttpStatus.OK);
    }

    @GetMapping("/get/{feedbackId}")
    public ResponseEntity<FeedbackConvertDTO> getFeedbackById(@PathVariable Long feedbackId) {
        FeedbackConvertDTO feedback = feedbackService.getFeedbackById(feedbackId);
        return feedback != null ? new ResponseEntity<>(feedback, HttpStatus.OK) :
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
