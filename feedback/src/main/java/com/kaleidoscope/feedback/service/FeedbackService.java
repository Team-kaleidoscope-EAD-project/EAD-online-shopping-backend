package com.kaleidoscope.feedback.service;

import com.kaleidoscope.feedback.dto.FeedbackDTO;
import com.kaleidoscope.feedback.dto.RatingDTO;
import com.kaleidoscope.feedback.model.Feedback;
import com.kaleidoscope.feedback.model.Rating;
import com.kaleidoscope.feedback.repo.FeedbackRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class FeedbackService {
    @Autowired
    private FeedbackRepo feedbackRepo;

    @Autowired
    private ModelMapper modelMapper;

    public FeedbackDTO addFeedback(FeedbackDTO feedbackDTO){
        feedbackRepo.save(modelMapper.map(feedbackDTO, Feedback.class));
        return feedbackDTO;
    }
    public List<FeedbackDTO> getAllFeedbacks(){
        List<Feedback>FeedbackList = feedbackRepo.findAll();
        return modelMapper.map(FeedbackList, new TypeToken<List<FeedbackDTO>>(){}.getType());
    }

    public List<FeedbackDTO> getFeedbackByProductId(String productId){
        List<Feedback> feedback = feedbackRepo.findByProductId(productId);
        return feedback.stream().map(f -> modelMapper.map(f, FeedbackDTO.class))
                .toList();
    }

    public List<FeedbackDTO> getFeedbackByUserId(String userId){
        List<Feedback> feedback = feedbackRepo.findByUserId(userId);
        return feedback.stream().map(f -> modelMapper.map(f, FeedbackDTO.class))
                .toList();
    }


    public FeedbackDTO updateFeedback(FeedbackDTO feedbackDTO){
        feedbackRepo.save(modelMapper.map(feedbackDTO, Feedback.class));
        return feedbackDTO;
    }

    public String deleteFeedback(Integer feedbackId){
//        feedbackRepo.delete(modelMapper.map(feedbackDTO, Feedback.class));
        feedbackRepo.deleteById(feedbackId);
        return "Feedback successfully deleted!";
    }
}
