package com.kaleidoscope.feedback.service;

import com.kaleidoscope.feedback.dto.RatingDTO;
import com.kaleidoscope.feedback.model.Rating;
import com.kaleidoscope.feedback.repo.RatingRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class) // Ensures Mockito initializes the mocks.
public class RatingServiceTest {

    private static final String USER_ID = "user1";
    private static final String PRODUCT_ID = "product1";
    private static final String ERROR_MESSAGE = "Database error";
    private static final int VALID_RATING = 4;
    private static final int INVALID_RATING = 6;

    @Mock
    private RatingRepo ratingRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private RatingService ratingService;

    private RatingDTO ratingDTO;
    private Rating rating;

    @BeforeEach
    void setUp() {
        // Initialize test data
        ratingDTO = new RatingDTO(1L, USER_ID, PRODUCT_ID, VALID_RATING, 101);
        rating = new Rating(null, USER_ID, PRODUCT_ID, VALID_RATING);
    }

    @Test
    void shouldAddRatingSuccessfully_whenValidRatingDTOIsProvided() {
        //Arrange
        when(modelMapper.map(any(RatingDTO.class), eq(Rating.class))).thenReturn(rating);
        when(ratingRepo.save(any(Rating.class))).thenReturn(rating);

        //Act
        RatingDTO result = ratingService.addRating(ratingDTO);

        //Assert
        assertEquals(ratingDTO.getUserId(), result.getUserId());
        assertEquals(ratingDTO.getProductId(), result.getProductId());
        assertEquals(ratingDTO.getRatingValue(), result.getRatingValue());
    }

    @Test
    void shouldThrowException_whenInvalidRatingValueIsProvided() {
        RatingDTO invalidRating = new RatingDTO(1L, USER_ID, PRODUCT_ID, INVALID_RATING, 101); // Invalid rating value

        when(modelMapper.map(any(RatingDTO.class), eq(Rating.class))).thenReturn(rating);
        when(ratingRepo.save(any(Rating.class))).thenThrow(new IllegalArgumentException("Invalid rating value"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ratingService.addRating(invalidRating));
        assertEquals("Invalid rating value", exception.getMessage());
    }

    @Test
    void shouldReturnRatings_whenProductIdIsValid() {
        List<Rating> ratings = Arrays.asList(rating);
        when(ratingRepo.findByProductId(PRODUCT_ID)).thenReturn(ratings);
        when(modelMapper.map(any(Rating.class), eq(RatingDTO.class))).thenReturn(ratingDTO);

        List<RatingDTO> result = ratingService.getRatingByProductId(PRODUCT_ID);

        assertEquals(1, result.size());
        assertEquals(PRODUCT_ID, result.get(0).getProductId());
    }

    @Test
    void shouldReturnEmptyList_whenNoRatingsAreFoundForProductId() {
        when(ratingRepo.findByProductId(PRODUCT_ID)).thenReturn(Collections.emptyList());

        List<RatingDTO> result = ratingService.getRatingByProductId(PRODUCT_ID);

        assertEquals(0, result.size());
    }

    @Test
    void shouldThrowException_whenDatabaseErrorOccursWhileFetchingRatingsByProductId() {
        when(ratingRepo.findByProductId(PRODUCT_ID)).thenThrow(new RuntimeException(ERROR_MESSAGE));

        Exception exception = assertThrows(RuntimeException.class, () -> ratingService.getRatingByProductId(PRODUCT_ID));
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    @Test
    void shouldReturnRatings_whenUserIdIsValid() {
        List<Rating> ratings = Arrays.asList(rating);
        when(ratingRepo.findByUserId(USER_ID)).thenReturn(ratings);
        when(modelMapper.map(any(Rating.class), eq(RatingDTO.class))).thenReturn(ratingDTO);

        List<RatingDTO> result = ratingService.getRatingByUserId(USER_ID);

        assertEquals(1, result.size());
        assertEquals(USER_ID, result.get(0).getUserId());
    }

    @Test
    void shouldReturnEmptyList_whenNoRatingsAreFoundForUserId() {
        when(ratingRepo.findByUserId(USER_ID)).thenReturn(Collections.emptyList());

        List<RatingDTO> result = ratingService.getRatingByUserId(USER_ID);

        assertEquals(0, result.size());
    }

    @Test
    void shouldThrowException_whenDatabaseErrorOccursWhileFetchingRatingsByUserId() {

        when(ratingRepo.findByUserId(USER_ID)).thenThrow(new RuntimeException(ERROR_MESSAGE));

        Exception exception = assertThrows(RuntimeException.class, () -> ratingService.getRatingByUserId(USER_ID));

        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
}
