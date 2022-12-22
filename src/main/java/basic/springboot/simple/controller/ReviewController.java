package basic.springboot.simple.controller;

import basic.springboot.simple.model.ApiResponse;
import basic.springboot.simple.model.DTO.ReviewDTO;
import basic.springboot.simple.service.ReviewService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path="/review")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping(path="/")
    public ResponseEntity<Object> add(@Valid @RequestBody ReviewDTO reviewDTO){

        return ApiResponse.send(reviewService.save(reviewDTO));
    }
}
