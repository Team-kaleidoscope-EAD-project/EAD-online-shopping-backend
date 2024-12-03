package com.kaleidoscope.feedback.repo;

import com.kaleidoscope.feedback.model.Feedback;
import lombok.NonNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@Repository
public interface FeedbackRepo extends JpaRepository<Feedback, Integer> {
    List<Feedback> findByUserId(@NonNull String userId);
    List<Feedback> findByProductId(@NonNull String productId);


}
