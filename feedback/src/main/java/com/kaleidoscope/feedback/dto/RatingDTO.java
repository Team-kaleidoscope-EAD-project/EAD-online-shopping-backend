package com.kaleidoscope.feedback.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RatingDTO {
    private Long id;
    private Long userId;
    private Long productId;
    private int ratingValue;
    private Integer feedbackId;

}
