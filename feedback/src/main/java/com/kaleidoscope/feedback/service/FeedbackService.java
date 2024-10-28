package com.kaleidoscope.feedback.service;

import com.kaleidoscope.feedback.dto.FeedbackDTO;
import com.kaleidoscope.feedback.model.Feedback;
import com.kaleidoscope.feedback.repo.FeedbackRepo;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
@Service
@Transactional

public class FeedbackService {
    @Autowired
    private FeedbackRepo feedbackRepo;

    @Autowired
    private ModelMapper modelMapper;

    public List<FeedbackDTO> getAllFeedbacks(){
        List<Feedback>FeedbackList = feedbackRepo.findAll();
        return modelMapper.map(FeedbackList, new TypeToken<List<FeedbackDTO>>(){}.getType());
    }

    public FeedbackDTO addFeedback(FeedbackDTO feedbackDTO){
        feedbackRepo.save(modelMapper.map(feedbackDTO, Feedback.class));
        return feedbackDTO;
    }
}
