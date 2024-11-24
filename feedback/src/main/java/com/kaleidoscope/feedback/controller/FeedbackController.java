package com.kaleidoscope.feedback.controller;

import com.kaleidoscope.feedback.dto.FeedbackDTO;
import com.kaleidoscope.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
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

    @GetMapping("/getfeedback/{feedbackId}")
    public FeedbackDTO findById(@PathVariable int feedbackId) {
        return feedbackService.getOneFeedback(feedbackId);
    }

    @PutMapping("/updatefeedback")
    public FeedbackDTO updateFeedback(@RequestBody FeedbackDTO feedbackDTO) {
        return feedbackService.updateFeedback(feedbackDTO);
    }

    @DeleteMapping("/deletefeedback/{feedbackId}")
    public String deleteFeedback(@PathVariable int feedbackId) {
        return feedbackService.deleteFeedback(feedbackId);
    }


}
