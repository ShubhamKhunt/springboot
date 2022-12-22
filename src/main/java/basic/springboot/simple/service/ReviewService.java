package basic.springboot.simple.service;

import basic.springboot.simple.entity.Review;
import basic.springboot.simple.model.DTO.ReviewDTO;

public interface ReviewService {
    public Review save(ReviewDTO reviewDTO);
}
