package com.erp.Ecommeres.homepage.service;



import org.springframework.stereotype.Service;

import com.erp.Ecommeres.homepage.dto.FeedbackRequestDTO;
import com.erp.Ecommeres.homepage.entity.CustomerFeedback;
import com.erp.Ecommeres.homepage.repo.CustomerFeedbackRepo;

import java.util.List;

@Service
public class CustomerFeedbackService {

    private final CustomerFeedbackRepo repository;

    public CustomerFeedbackService(CustomerFeedbackRepo repository) {
        this.repository = repository;
    }

    // Save feedback
    public CustomerFeedback saveFeedback(FeedbackRequestDTO dto) {

        CustomerFeedback feedback = new CustomerFeedback();
        feedback.setUserId(dto.getUserId());
        feedback.setProductId(dto.getProductId());
        feedback.setRating(dto.getRating());
        feedback.setComment(dto.getComment());

        return repository.save(feedback);
    }

    // Get feedback by product
    public List<CustomerFeedback> getFeedbackByProduct(Long productId) {
        return repository.findByProductId(productId);
    }
}
