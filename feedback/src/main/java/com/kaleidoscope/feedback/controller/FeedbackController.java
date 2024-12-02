package com.kaleidoscope.feedback.controller;

import com.kaleidoscope.feedback.dto.FeedbackDTO;
import com.kaleidoscope.feedback.dto.FeedbackWithRatingDTO;
import com.kaleidoscope.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController

@RequestMapping(value = "api/v1/feedback")
public class FeedbackController {
    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/addfeedback")
    public FeedbackDTO addFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        return feedbackService.addFeedback(feedbackDTO);
    }

    @GetMapping("/getfeedbacks")
    public List<FeedbackDTO> getFeedbacks() {
        return feedbackService.getAllFeedbacks();
    }

    @GetMapping("/getfeedback/byproduct/{productId}")
    public List<FeedbackDTO> findByProductId(@PathVariable String productId) {
        return feedbackService.getFeedbackByProductId(productId);
    }

    @GetMapping("/getfeedback/byuser/{userId}")
    public List<FeedbackDTO> findByUserId(@PathVariable String userId) {
        return feedbackService.getFeedbackByUserId(userId);
    }

    @PutMapping("/updatefeedback/{feedbackId}")
    public FeedbackDTO updateFeedback(@RequestBody FeedbackDTO feedbackDTO) { 
        return feedbackService.updateFeedback(feedbackDTO);
    }

    @DeleteMapping("/deletefeedback/{feedbackId}")
    public String deleteFeedback(@PathVariable int feedbackId) {
        return feedbackService.deleteFeedback(feedbackId);
    }


}
