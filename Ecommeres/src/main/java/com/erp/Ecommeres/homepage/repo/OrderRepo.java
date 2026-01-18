package com.erp.Ecommeres.homepage.repo;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.erp.Ecommeres.homepage.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {
    Optional<Order> findByRazorpayOrderId(String razorpayOrderId);

	@Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.status = 'PAID'")
    double getTotalRevenue();

	List<Order> findTop5ByOrderByCreatedAtDesc();
}

