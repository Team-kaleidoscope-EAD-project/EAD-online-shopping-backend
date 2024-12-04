package com.kaleidoscope.feedback.service;

import com.kaleidoscope.feedback.dto.RatingDTO;
import com.kaleidoscope.feedback.model.Rating;
import com.kaleidoscope.feedback.repo.RatingRepo;
import com.kaleidoscope.feedback.repo.FeedbackRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class) // This ensures Mockito initializes the mocks.
public class RatingServiceTest {

    @Mock
    private RatingRepo ratingRepo;

    @Mock
    private FeedbackRepo feedbackRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RatingService ratingService;

    private RatingDTO ratingDTO;
    private Rating rating;

    @BeforeEach
    void setUp() {
        // Initialize test data
        ratingDTO = new RatingDTO(1L, "user1", "product1", 4, 101);
        rating = new Rating(null, "user1", "product1", 4);
    }

    @Test
    void testAddRating() {
        //Arrange
        when(modelMapper.map(any(RatingDTO.class), any(Class.class))).thenReturn(rating);
        when(ratingRepo.save(any(Rating.class))).thenReturn(rating);

        //Act
        RatingDTO result = ratingService.addRating(ratingDTO);

        //Assert
        assertEquals(ratingDTO.getUserId(), result.getUserId());
        assertEquals(ratingDTO.getProductId(), result.getProductId());
        assertEquals(ratingDTO.getRatingValue(), result.getRatingValue());
    }

    @Test
    void testGetRatingByProductId() {
        List<Rating> ratings = Arrays.asList(rating);
        when(ratingRepo.findByProductId("product1")).thenReturn(ratings);
        when(modelMapper.map(any(Rating.class), any(Class.class))).thenReturn(ratingDTO);

        List<RatingDTO> result = ratingService.getRatingByProductId("product1");

        assertEquals(1, result.size());
        assertEquals("product1", result.get(0).getProductId());
    }

    @Test
    void testGetRatingByUserId() {
        List<Rating> ratings = Arrays.asList(rating);
        when(ratingRepo.findByUserId("user1")).thenReturn(ratings);
        when(modelMapper.map(any(Rating.class), any(Class.class))).thenReturn(ratingDTO);

        List<RatingDTO> result = ratingService.getRatingByUserId("user1");

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUserId());
    }
}

