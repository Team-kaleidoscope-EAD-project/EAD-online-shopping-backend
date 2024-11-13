package com.kaleidoscope.feedback.repo;

import com.kaleidoscope.feedback.model.Feedback;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import java.util.Optional;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {

    @SuppressWarnings("NullableProblems")
    @Override
    Optional<Feedback> findById(@NonNull Integer feedbackId);
}
