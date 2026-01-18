package com.erp.Ecommeres.homepage.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.Ecommeres.homepage.dto.FeedbackRequestDTO;
import com.erp.Ecommeres.homepage.entity.CustomerFeedback;
import com.erp.Ecommeres.homepage.service.CustomerFeedbackService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/feedback")
@CrossOrigin
public class CustomerFeedbackController {

    private final CustomerFeedbackService service;

    public CustomerFeedbackController(CustomerFeedbackService service) {
        this.service = service;
    }

    // POST feedback (Store rating)
    @PostMapping
    public CustomerFeedback submitFeedback(
            @Valid @RequestBody FeedbackRequestDTO dto) {
        return service.saveFeedback(dto);
    }

    // GET feedback for product
    @GetMapping("/product/{productId}")
    public List<CustomerFeedback> getFeedback(
            @PathVariable Long productId) {
        return service.getFeedbackByProduct(productId);
    }
}
