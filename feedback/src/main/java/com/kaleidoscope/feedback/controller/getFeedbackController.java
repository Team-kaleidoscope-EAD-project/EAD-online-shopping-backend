package com.kaleidoscope.feedback.controller;

import com.kaleidoscope.feedback.dto.FeedbackDTO;
import com.kaleidoscope.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/")
public class getFeedbackController {
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
}
