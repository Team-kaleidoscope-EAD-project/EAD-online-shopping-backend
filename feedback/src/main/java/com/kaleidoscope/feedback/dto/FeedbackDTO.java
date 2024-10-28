package com.kaleidoscope.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class FeedbackDTO {
    private int id;
    private String feedbackName;
}
