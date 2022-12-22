package basic.springboot.simple.service.impl;

import basic.springboot.simple.entity.Review;
import basic.springboot.simple.entity.SalesOrder;
import basic.springboot.simple.entity.User;
import basic.springboot.simple.exception.NoDataFoundException;
import basic.springboot.simple.model.DTO.ReviewDTO;
import basic.springboot.simple.repository.ReviewRepository;
import basic.springboot.simple.repository.SalesOrderRepository;
import basic.springboot.simple.repository.UserRepository;
import basic.springboot.simple.service.BaseService;
import basic.springboot.simple.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private SalesOrderRepository salesOrderRepository;

    @Autowired
    private BaseService baseService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Review save(ReviewDTO reviewDTO) {

        Optional<SalesOrder> salesOrder = salesOrderRepository.findById(Integer.valueOf(reviewDTO.getSalesOrderId()));
        if (!salesOrder.isPresent()) {
            throw new NoDataFoundException("SalesOrder not found");
        }

        User user = baseService.getCurrentUser();
        if (Objects.isNull(user)) {
            throw new NoDataFoundException("User not found");
        }

        Review review = modelMapper.map(reviewDTO, Review.class);
        review.setSalesOrder(salesOrder.get());
        review.setCreatedBy(user);
        review.setCreated(new Date());
        review.setModified(new Date());

        return reviewRepository.save(review);
    }
}
