package com.kaleidoscope.feedback.repo;

import com.kaleidoscope.feedback.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;
import java.util.Optional;
import java.util.List;

public interface RatingRepo extends JpaRepository<Rating, Long> {

    List<Rating> findByProductId(@NonNull Long productId);
    List<Rating>findByUserId(@NonNull Long userId);
}
