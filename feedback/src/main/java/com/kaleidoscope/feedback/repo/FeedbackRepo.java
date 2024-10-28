package com.kaleidoscope.feedback.repo;

import com.kaleidoscope.feedback.model.Feedback;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository

public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {
}
