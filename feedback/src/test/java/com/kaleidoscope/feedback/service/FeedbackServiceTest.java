package com.kaleidoscope.feedback.service;

import com.kaleidoscope.feedback.dto.FeedbackDTO;
import com.kaleidoscope.feedback.model.Feedback;
import com.kaleidoscope.feedback.repo.FeedbackRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class FeedbackServiceTest {

    private static final String USER_ID = "User456";
    private static final String PRODUCT_ID = "Product123";
    private static final String ERROR_MESSAGE = "Database error";

    @Mock
    private FeedbackRepo feedbackRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private FeedbackService feedbackService;

    private FeedbackDTO feedbackDTO;
    private Feedback feedback;

    @BeforeEach
    void setUp() {
        // Initialize test data
        feedbackDTO = new FeedbackDTO(
                1L,
                LocalDateTime.now(),
                "Great product!",
                PRODUCT_ID,
                USER_ID,
                5
        );

    }

    // Add Feedback Tests
    @Test
    void shouldAddFeedbackSuccessfully_whenValidFeedbackDTOIsProvided() {
        when(modelMapper.map(any(FeedbackDTO.class), eq(Feedback.class))).thenReturn(feedback);
        when(feedbackRepo.save(any(Feedback.class))).thenReturn(feedback);

        FeedbackDTO result = feedbackService.addFeedback(feedbackDTO);

        assertEquals(feedbackDTO.getProductId(), result.getProductId());
        assertEquals(feedbackDTO.getUserId(), result.getUserId());
        assertEquals(feedbackDTO.getFeedbackContent(), result.getFeedbackContent());
    }

    // Get All Feedbacks Tests
    @Test
    void shouldReturnAllFeedbacksSuccessfully() {
        List<Feedback> feedbackList = Arrays.asList(feedback);
        when(feedbackRepo.findAll()).thenReturn(feedbackList);
        when(modelMapper.map(any(), eq(FeedbackDTO.class))).thenReturn(feedbackDTO);

        List<FeedbackDTO> result = feedbackService.getAllFeedbacks();

        assertEquals(1, result.size());
        assertEquals(PRODUCT_ID, result.get(0).getProductId());
    }

    @Test
    void shouldReturnEmptyList_whenNoFeedbacksExist() {
        when(feedbackRepo.findAll()).thenReturn(Collections.emptyList());

        List<FeedbackDTO> result = feedbackService.getAllFeedbacks();

        assertTrue(result.isEmpty(), "Result should be empty when no feedbacks are found");
    }

    // Get Feedbacks by Product ID Tests
    @Test
    void shouldReturnFeedbacksByProductIdSuccessfully() {
        List<Feedback> feedbackList = Arrays.asList(feedback);
        when(feedbackRepo.findByProductId(PRODUCT_ID)).thenReturn(feedbackList);
        when(modelMapper.map(any(Feedback.class), eq(FeedbackDTO.class))).thenReturn(feedbackDTO);

        List<FeedbackDTO> result = feedbackService.getFeedbackByProductId(PRODUCT_ID);

        assertEquals(1, result.size());
        assertEquals(PRODUCT_ID, result.get(0).getProductId());
    }

    @Test
    void shouldReturnEmptyList_whenNoFeedbacksFoundForProductId() {
        when(feedbackRepo.findByProductId(PRODUCT_ID)).thenReturn(Collections.emptyList());

        List<FeedbackDTO> result = feedbackService.getFeedbackByProductId(PRODUCT_ID);

        assertTrue(result.isEmpty(), "Result should be empty for an invalid product ID");
    }

    @Test
    void shouldThrowException_whenDatabaseErrorOccursWhileFetchingFeedbacksByProductId() {
        when(feedbackRepo.findByProductId(PRODUCT_ID)).thenThrow(new RuntimeException(ERROR_MESSAGE));

        Exception exception = assertThrows(RuntimeException.class, () -> feedbackService.getFeedbackByProductId(PRODUCT_ID));
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    // Get Feedbacks by User ID Tests
    @Test
    void shouldReturnFeedbacksByUserIdSuccessfully() {
        List<Feedback> feedbackList = Arrays.asList(feedback);
        when(feedbackRepo.findByUserId(USER_ID)).thenReturn(feedbackList);
        when(modelMapper.map(any(Feedback.class), eq(FeedbackDTO.class))).thenReturn(feedbackDTO);

        List<FeedbackDTO> result = feedbackService.getFeedbackByUserId(USER_ID);

        assertEquals(1, result.size());
        assertEquals(USER_ID, result.get(0).getUserId());
    }

    @Test
    void shouldReturnEmptyList_whenNoFeedbacksFoundForUserId() {
        when(feedbackRepo.findByUserId(USER_ID)).thenReturn(Collections.emptyList());

        List<FeedbackDTO> result = feedbackService.getFeedbackByUserId(USER_ID);

        assertTrue(result.isEmpty(), "Result should be empty for an invalid user ID");
    }

    @Test
    void shouldThrowException_whenDatabaseErrorOccursWhileFetchingFeedbacksByUserId() {
        when(feedbackRepo.findByUserId(USER_ID)).thenThrow(new RuntimeException(ERROR_MESSAGE));

        Exception exception = assertThrows(RuntimeException.class, () -> feedbackService.getFeedbackByUserId(USER_ID));
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }

    // Delete Feedback Tests
    @Test
    void shouldDeleteFeedbackSuccessfully_whenFeedbackIdIsValid() {
        when(feedbackRepo.existsById(1)).thenReturn(true);

        String result = feedbackService.deleteFeedback(1);

        assertEquals("Feedback successfully deleted!", result);
    }

    @Test
    void shouldThrowException_whenFeedbackIdIsInvalid() {
        when(feedbackRepo.existsById(-1)).thenReturn(false);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> feedbackService.deleteFeedback(-1));
        assertEquals("Feedback ID not found", exception.getMessage());
    }

    @Test
    void shouldThrowException_whenUnexpectedErrorOccursDuringDeletion() {
        when(feedbackRepo.existsById(1)).thenThrow(new RuntimeException(ERROR_MESSAGE));

        Exception exception = assertThrows(RuntimeException.class, () -> feedbackService.deleteFeedback(1));
        assertEquals(ERROR_MESSAGE, exception.getMessage());
    }
}

