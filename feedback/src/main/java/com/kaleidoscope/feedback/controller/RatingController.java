package com.kaleidoscope.feedback.controller;

import com.kaleidoscope.feedback.dto.RatingDTO;
import com.kaleidoscope.feedback.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("api/v1/rating")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping("/addrating")
    public RatingDTO addRating(@RequestBody RatingDTO ratingDTO) {
        return ratingService.addRating(ratingDTO);
    }

    @GetMapping("/getrating/byproduct/{productId}")
    public List<RatingDTO> findByProductId(@PathVariable String productId) {
        return ratingService.getRatingByProductId(productId);
    }

    @GetMapping("/getrating/byuser/{userId}")
    public List<RatingDTO> findByUserId(@PathVariable String userId) {
        return ratingService.getRatingByUserId(userId);
    }
}
