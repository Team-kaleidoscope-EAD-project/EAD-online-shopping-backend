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
        private Long productId;
        private Long userId;

}
