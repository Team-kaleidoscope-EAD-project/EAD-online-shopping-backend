package com.kaleidoscope.feedback.controller;

import com.kaleidoscope.feedback.dto.FeedbackDTO;
import com.kaleidoscope.feedback.dto.FeedbackWithRatingDTO;
import com.kaleidoscope.feedback.dto.RatingDTO;
import com.kaleidoscope.feedback.model.Feedback;
import com.kaleidoscope.feedback.service.FeedbackService;
import com.kaleidoscope.feedback.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(value = "api/v1/feedback")
public class FeedbackWithRatingController {

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private RatingService ratingService;

    @PostMapping("/feedbackandrating")
    public FeedbackWithRatingDTO AddFeedbackWithRating(@RequestBody FeedbackWithRatingDTO feedbackWithRatingDTO) {
        FeedbackDTO feedbackResponse = new FeedbackDTO();
        RatingDTO ratingResponse = new RatingDTO();
        ratingResponse = ratingService.addRating(feedbackWithRatingDTO.getRatings());
        feedbackResponse = feedbackService.addFeedback(feedbackWithRatingDTO.getFeedback());
        return feedbackWithRatingDTO;
    }

    @GetMapping("/feedbackandrating/{userId}")
    public List<FeedbackWithRatingDTO> getFeedbackWithRating(@PathVariable Long userId) {
        // Fetch the feedback and ratings for the user
        List<FeedbackDTO> feedbackResponse = feedbackService.getFeedbackByUserId(userId);
        List<RatingDTO> ratingResponse = ratingService.getRatingByUserId(userId);

        // Ensure the sizes of feedback and rating lists match, if applicable
        if (feedbackResponse.size() != ratingResponse.size()) {
            throw new IllegalStateException("Mismatch between feedback and rating sizes");
        }

        // Combine feedback and ratings into FeedbackWithRatingDTO objects
        List<FeedbackWithRatingDTO> feedbackWithRatingDTOList = new ArrayList<>();
        for (int i = 0; i < feedbackResponse.size(); i++) {
            FeedbackWithRatingDTO dto = new FeedbackWithRatingDTO();
            dto.setFeedback(feedbackResponse.get(i));
            dto.setRatings(ratingResponse.get(i));
            feedbackWithRatingDTOList.add(dto);
        }

        return feedbackWithRatingDTOList;
    }

}
