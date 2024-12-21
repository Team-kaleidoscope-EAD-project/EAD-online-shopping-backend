package com.kaleidoscope.feedback.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class RatingDTO {
    private Long id;
    private String userId;
    private String productId;
    private Integer ratingValue;
    private Integer feedbackId;

}
