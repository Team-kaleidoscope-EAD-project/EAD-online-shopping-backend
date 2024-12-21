package com.kaleidoscope.feedback.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackWithRatingDTO {
    private FeedbackDTO feedback;
    private RatingDTO ratings;
}
