package com.kaleidoscope.feedback.controller;

import com.kaleidoscope.feedback.dto.FeedbackDTO;
import com.kaleidoscope.feedback.service.FeedbackService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class FeedbackControllerTest {

    @Mock
    private FeedbackService feedbackService;

    @InjectMocks
    private FeedbackController feedbackController;

    private FeedbackDTO feedbackDTO;

    @BeforeEach
    void setUp() {
        // Initialize Mockito annotations
        MockitoAnnotations.openMocks(this);

        // Prepare test data
        feedbackDTO = new FeedbackDTO(
                1L,
                LocalDateTime.now(),
                "Great product!",
                "Product123",
                "User456",
                5
        );
    }


    @Test
    void shouldAddFeedbackSuccessfully() {
        //Arrange
        when(feedbackService.addFeedback(feedbackDTO)).thenReturn(feedbackDTO);

        //Act
        FeedbackDTO result = feedbackController.addFeedback(feedbackDTO);

        //Assert
        assertEquals(feedbackDTO.getFeedbackId(), result.getFeedbackId());
        assertEquals(feedbackDTO.getProductId(), result.getProductId());
        assertEquals(feedbackDTO.getUserId(), result.getUserId());
        assertEquals(feedbackDTO.getFeedbackContent(), result.getFeedbackContent());
        assertEquals(feedbackDTO.getRating(), result.getRating());
    }

    @Test
    void shouldReturnNullWhenAddFeedbackInputIsNull() {
        when(feedbackService.addFeedback(null)).thenReturn(null);

        FeedbackDTO result = feedbackController.addFeedback(null);

        assertNull(result, "Result should be null when input is null");
    }

    @Test
    void shouldThrowExceptionWhenUnexpectedErrorOccursWhileAddingFeedback() {
        when(feedbackService.addFeedback(feedbackDTO)).thenThrow(new RuntimeException("Unexpected error"));

        Exception exception = assertThrows(RuntimeException.class, () -> feedbackController.addFeedback(feedbackDTO));

        assertEquals("Unexpected error", exception.getMessage());
    }


    @Test
    void shouldGetAllFeedbacksSuccessfully() {
        List<FeedbackDTO> feedbackList = List.of(feedbackDTO);
        when(feedbackService.getAllFeedbacks()).thenReturn(feedbackList);

        List<FeedbackDTO> result = feedbackController.getFeedbacks();

        assertEquals(1, result.size());
        assertEquals(feedbackDTO.getProductId(), result.get(0).getProductId());
    }

    @Test
    void shouldReturnEmptyListWhenNoFeedbacksFound() {
        when(feedbackService.getAllFeedbacks()).thenReturn(Collections.emptyList());

        List<FeedbackDTO> result = feedbackController.getFeedbacks();

        assertTrue(result.isEmpty(), "Result should be empty when no feedbacks are found");
    }

    @Test
    void shouldHandleErrorWhenUnexpectedErrorOccursWhileFetchingFeedbacks() {
        when(feedbackService.getAllFeedbacks()).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(RuntimeException.class, feedbackController::getFeedbacks);

        assertEquals("Database error", exception.getMessage());
    }


    @Test
    void shouldGetFeedbacksByProductIdSuccessfully() {
        when(feedbackService.getFeedbackByProductId("Product123")).thenReturn(List.of(feedbackDTO));

        List<FeedbackDTO> result = feedbackController.findByProductId("Product123");

        assertEquals(1, result.size());
        assertEquals(feedbackDTO.getProductId(), result.get(0).getProductId());
    }

    @Test
    void shouldReturnEmptyListForInvalidProductId() {
        when(feedbackService.getFeedbackByProductId("InvalidProduct")).thenReturn(Collections.emptyList());

        List<FeedbackDTO> result = feedbackController.findByProductId("InvalidProduct");

        assertTrue(result.isEmpty(), "Result should be empty for an invalid product ID");
    }

    @Test
    void shouldThrowExceptionForUnexpectedErrorWhenFetchingFeedbacksByProductId() {
        when(feedbackService.getFeedbackByProductId("Product123")).thenThrow(new RuntimeException("Unexpected error"));

        Exception exception = assertThrows(RuntimeException.class, () -> feedbackController.findByProductId("Product123"));

        assertEquals("Unexpected error", exception.getMessage());
    }


    @Test
    void shouldGetFeedbacksByUserIdSuccessfully() {
        when(feedbackService.getFeedbackByUserId("User456")).thenReturn(List.of(feedbackDTO));

        List<FeedbackDTO> result = feedbackController.findByUserId("User456");

        assertEquals(1, result.size());
        assertEquals(feedbackDTO.getUserId(), result.get(0).getUserId());
    }

    @Test
    void shouldReturnEmptyListForInvalidUserId() {
        when(feedbackService.getFeedbackByUserId("InvalidUser")).thenReturn(Collections.emptyList());

        List<FeedbackDTO> result = feedbackController.findByUserId("InvalidUser");

        assertTrue(result.isEmpty(), "Result should be empty for an invalid user ID");
    }

    @Test
    void shouldThrowExceptionForUnexpectedErrorWhenFetchingFeedbacksByUserId() {
        when(feedbackService.getFeedbackByUserId("User456")).thenThrow(new RuntimeException("Unexpected error"));

        Exception exception = assertThrows(RuntimeException.class, () -> feedbackController.findByUserId("User456"));

        assertEquals("Unexpected error", exception.getMessage());
    }


    @Test
    void shouldDeleteFeedbackSuccessfully() {
        when(feedbackService.deleteFeedback(1)).thenReturn("Feedback deleted successfully");

        String result = feedbackController.deleteFeedback(1);

        assertEquals("Feedback deleted successfully", result);
    }

    @Test
    void shouldThrowExceptionForInvalidFeedbackId() {
        when(feedbackService.deleteFeedback(-1)).thenThrow(new IllegalArgumentException("Invalid feedback ID"));

        Exception exception = assertThrows(IllegalArgumentException.class, () -> feedbackController.deleteFeedback(-1));

        assertEquals("Invalid feedback ID", exception.getMessage());
    }

    @Test
    void shouldThrowExceptionForUnexpectedErrorWhileDeletingFeedback() {
        when(feedbackService.deleteFeedback(1)).thenThrow(new RuntimeException("Unexpected error"));

        Exception exception = assertThrows(RuntimeException.class, () -> feedbackController.deleteFeedback(1));

        assertEquals("Unexpected error", exception.getMessage());
    }
}
