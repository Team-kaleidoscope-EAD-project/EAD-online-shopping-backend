package com.kaleidoscope.feedback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;


@Data
@NoArgsConstructor
@AllArgsConstructor

public class FeedbackDTO {

        private Long feedbackId;
        private LocalDateTime dateTime;
        private String feedbackContent;
        private String productId;
        private String userId;
        private Integer rating;

    public FeedbackDTO(int i, String product123, String user456, String greatProduct, int i1) {
    }
}
