package com.kaleidoscope.feedback.controller;

import com.kaleidoscope.feedback.dto.RatingDTO;
import com.kaleidoscope.feedback.service.RatingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void testAddRating() {
        //Arrange
        // Mock the service call
        when(ratingService.addRating(ratingDTO)).thenReturn(ratingDTO);

        //Act
        // Call the controller method directly
        RatingDTO result = ratingController.addRating(ratingDTO);

        //Assert
        // Verify the response
        assertEquals(ratingDTO.getUserId(), result.getUserId());
        assertEquals(ratingDTO.getProductId(), result.getProductId());
        assertEquals(ratingDTO.getRatingValue(), result.getRatingValue());
    }

    @Test
    void testFindByProductId() {
        // Mock the service call
        when(ratingService.getRatingByProductId("product1")).thenReturn(List.of(ratingDTO));

        // Call the controller method directly
        List<RatingDTO> result = ratingController.findByProductId("product1");

        // Verify the response
        assertEquals(1, result.size());
        assertEquals("product1", result.get(0).getProductId());
        assertEquals(4, result.get(0).getRatingValue());
    }

    @Test
    void testFindByUserId() {
        // Mock the service call
        when(ratingService.getRatingByUserId("user1")).thenReturn(List.of(ratingDTO));

        // Call the controller method directly
        List<RatingDTO> result = ratingController.findByUserId("user1");

        // Verify the response
        assertEquals(1, result.size());
        assertEquals("user1", result.get(0).getUserId());
        assertEquals(4, result.get(0).getRatingValue());
    }
}
