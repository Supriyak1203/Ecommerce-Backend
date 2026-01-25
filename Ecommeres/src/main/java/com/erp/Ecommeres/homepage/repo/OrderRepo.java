package com.erp.Ecommeres.homepage.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.erp.Ecommeres.homepage.entity.Order;

public interface OrderRepo extends JpaRepository<Order, Long> {

    Optional<Order> findByRazorpayOrderId(String razorpayOrderId);

    @Query("SELECT COALESCE(SUM(o.totalPrice), 0) FROM Order o WHERE o.status = 'PAID'")
    double getTotalRevenue();

    List<Order> findTop5ByOrderByCreatedAtDesc();

    // 🔍 SEARCH ORDERS (safe for Long fields)
    @Query("""
        SELECT o FROM Order o
        WHERE 
           CAST(o.id AS string) LIKE %:keyword%
        OR CAST(o.userId AS string) LIKE %:keyword%
        OR LOWER(o.productName) LIKE LOWER(CONCAT('%', :keyword, '%'))
        OR CAST(o.productId AS string) LIKE %:keyword%
    """)
    List<Order> searchOrders(@Param("keyword") String keyword);

    // 🔍 SEARCH PAYMENTS
    @Query("""
        SELECT o FROM Order o
        WHERE 
           CAST(o.id AS string) LIKE %:keyword%
        OR CAST(o.userId AS string) LIKE %:keyword%
        OR LOWER(COALESCE(o.razorpayPaymentId, '')) 
              LIKE LOWER(CONCAT('%', :keyword, '%'))
    """)
    List<Order> searchPayments(@Param("keyword") String keyword);
    
    @Query("""
    		SELECT COALESCE(SUM(o.totalPrice),0)
    		FROM Order o
    		WHERE o.status IN ('PAID','SHIPPED','DELIVERED')
    		""")
    		double getTotalRevenue1();


}
