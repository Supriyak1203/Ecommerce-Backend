package com.erp.Ecommeres.homepage.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.erp.Ecommeres.homepage.entity.CustomerFeedback;

import java.util.List;

public interface CustomerFeedbackRepo
        extends JpaRepository<CustomerFeedback, Long> {

    List<CustomerFeedback> findByProductId(Long productId);
}
