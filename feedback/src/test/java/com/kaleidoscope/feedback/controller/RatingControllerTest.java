package com.kaleidoscope.feedback.controller;

import com.kaleidoscope.feedback.dto.RatingDTO;
import com.kaleidoscope.feedback.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

public class RatingControllerTest {

    @Mock
    private RatingService ratingService;

    @InjectMocks
    private RatingController ratingController;

    private RatingDTO ratingDTO;

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Prepare test data
        ratingDTO = new RatingDTO(1L, "user1", "product1", 4, 101);
    }

    @Test
    void shouldAddRatingSuccessfully() {
        // Arrange
        when(ratingService.addRating(ratingDTO)).thenReturn(ratingDTO);

        // Act
        RatingDTO result = ratingController.addRating(ratingDTO);

        // Assert
        assertEquals(ratingDTO.getUserId(), result.getUserId());
        assertEquals(ratingDTO.getProductId(), result.getProductId());
        assertEquals(ratingDTO.getRatingValue(), result.getRatingValue());
    }

    @Test
    void shouldReturnNullWhenAddRatingInputIsNull() {
        when(ratingService.addRating(null)).thenReturn(null);

        RatingDTO result = ratingController.addRating(null);

        assertNull(result, "Result should be null when input is null");
    }

    @Test
    void shouldThrowExceptionWhenInvalidRatingValueIsProvided() {
        RatingDTO invalidRating = new RatingDTO(1L, "user1", "product1", 10, 101); // Invalid rating value

        when(ratingService.addRating(invalidRating)).thenThrow(new IllegalArgumentException("Invalid rating value"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> ratingController.addRating(invalidRating));
        assertEquals("Invalid rating value", exception.getMessage());
    }

    @Test
    void shouldReturnInternalServerErrorWhenUnexpectedErrorOccurs() {
        when(ratingService.addRating(ratingDTO)).thenThrow(new RuntimeException("Unexpected error"));

        Exception exception = assertThrows(RuntimeException.class, () -> ratingController.addRating(ratingDTO));
        assertEquals("Unexpected error", exception.getMessage());
    }

    @Test
    void shouldFindRatingsByProductIdSuccessfully() {
        when(ratingService.getRatingByProductId("product1")).thenReturn(List.of(ratingDTO));

        List<RatingDTO> result = ratingController.findByProductId("product1");

        assertEquals(1, result.size());
        assertEquals("product1", result.get(0).getProductId());
        assertEquals(4, result.get(0).getRatingValue());
    }

    @Test
    void shouldReturnEmptyListForInvalidProductId() {
        String invalidProductId = "invalidProduct";
        when(ratingService.getRatingByProductId(invalidProductId)).thenReturn(Collections.emptyList());

        List<RatingDTO> result = ratingController.findByProductId(invalidProductId);

        assertTrue(result.isEmpty(), "Result should be empty for an invalid product ID");
    }

    @Test
    void shouldReturnEmptyListForNullProductId() {
        when(ratingService.getRatingByProductId(null)).thenReturn(Collections.emptyList());

        List<RatingDTO> result = ratingController.findByProductId(null);

        assertTrue(result.isEmpty(), "Result should be empty for a null product ID");
    }

    @Test
    void shouldReturnEmptyListForProductIdWithNoRatings() {
        when(ratingService.getRatingByProductId("unknownProduct")).thenReturn(Collections.emptyList());

        List<RatingDTO> result = ratingController.findByProductId("unknownProduct");

        assertTrue(result.isEmpty(), "Result should be empty for an unknown product ID");
    }

    @Test
    void shouldReturnInternalServerErrorWhenDatabaseErrorOccurs() {
        when(ratingService.getRatingByProductId("product1")).thenThrow(new RuntimeException("Database connection error"));

        Exception exception = assertThrows(RuntimeException.class, () -> ratingController.findByProductId("product1"));
        assertEquals("Database connection error", exception.getMessage());
    }

    @Test
    void shouldFindRatingsByUserIdSuccessfully() {
        when(ratingService.getRatingByUserId("user1")).thenReturn(List.of(ratingDTO));

        List<RatingDTO> result = ratingController.findByUserId("user1");

        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUserId());
        assertEquals(4, result.get(0).getRatingValue());
    }

    @Test
    void shouldReturnEmptyListForMalformedUserId() {
        String malformedUserId = "user!@#"; // Simulate a malformed userId
        when(ratingService.getRatingByUserId(malformedUserId)).thenReturn(Collections.emptyList());

        List<RatingDTO> result = ratingController.findByUserId(malformedUserId);

        assertTrue(result.isEmpty(), "Result should be empty for a malformed user ID");
    }

    @Test
    void shouldReturnEmptyListForNullUserId() {
        when(ratingService.getRatingByUserId(null)).thenReturn(Collections.emptyList());

        List<RatingDTO> result = ratingController.findByUserId(null);

        assertTrue(result.isEmpty(), "Result should be empty for a null user ID");
    }

    @Test
    void shouldReturnEmptyListForUserIdWithNoRatings() {
        when(ratingService.getRatingByUserId("unknownUser")).thenReturn(Collections.emptyList());

        List<RatingDTO> result = ratingController.findByUserId("unknownUser");

        assertTrue(result.isEmpty(), "Result should be empty for an unknown user ID");
    }
}
