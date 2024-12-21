package com.kaleidoscope.feedback.service;

import com.kaleidoscope.feedback.model.Rating;
import com.kaleidoscope.feedback.dto.RatingDTO;
import com.kaleidoscope.feedback.repo.RatingRepo;
import com.kaleidoscope.feedback.repo.FeedbackRepo;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class RatingService {
    @Autowired
    private  RatingRepo ratingRepo;

    @Autowired
    private FeedbackRepo feedbackRepo;

    @Autowired
    private ModelMapper modelMapper;

    public RatingDTO addRating(RatingDTO ratingDTO){
        ratingRepo.save(modelMapper.map(ratingDTO, Rating.class));
        return ratingDTO;
    }

    public List<RatingDTO> getRatingByProductId(String productId){
        List<Rating> rating = ratingRepo.findByProductId(productId);
        return rating.stream().map(rate -> modelMapper.map(rate, RatingDTO.class))
                .toList();
    }

    public List<RatingDTO> getRatingByUserId(String userId){
        List<Rating> rating = ratingRepo.findByUserId(userId);
        return rating.stream().map(rate -> modelMapper.map(rate, RatingDTO.class))
                .toList();
    }
}
