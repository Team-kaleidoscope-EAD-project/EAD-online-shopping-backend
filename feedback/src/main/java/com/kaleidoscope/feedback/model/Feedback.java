package com.kaleidoscope.feedback.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Feedback {
    @Id //primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // automatic ID generator.
    @Column(name = "feedback_id")
    private int feedbackId;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    private String feedbackContent;

//    @ManyToOne
//    private User user;

//    @ManyToOne
//    private Product product;

}
