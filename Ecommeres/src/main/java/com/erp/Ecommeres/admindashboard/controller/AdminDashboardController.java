package com.erp.Ecommeres.admindashboard.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.Ecommeres.admindashboard.dto.DashboardResponseDTO;
import com.erp.Ecommeres.admindashboard.dto.RecentOrderDTO;
import com.erp.Ecommeres.admindashboard.repo.ProductRepo;
import com.erp.Ecommeres.entity.User;
import com.erp.Ecommeres.homepage.entity.Order;
import com.erp.Ecommeres.homepage.repo.OrderRepo;
import com.erp.Ecommeres.repo.UserRepo;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminDashboardController {

    @Autowired
    private UserRepo userRepository;

    @Autowired
    private ProductRepo productRepository;

    @Autowired
    private OrderRepo orderRepository;

    // ===================== DASHBOARD CARDS =====================
    @GetMapping("/dashboard")
    public DashboardResponseDTO getDashboardData() {

        DashboardResponseDTO response = new DashboardResponseDTO();

        response.setTotalCustomers(userRepository.count());
        response.setTotalProducts(productRepository.count());
        response.setTotalOrders(orderRepository.count());
        response.setTotalRevenue(orderRepository.getTotalRevenue());

        return response;
    }

    @GetMapping("/recent-orders")
    public List<RecentOrderDTO> getRecentOrders() {

        List<Order> orders =
                orderRepository.findTop5ByOrderByCreatedAtDesc();

        return orders.stream()
                .map(order -> {

                    RecentOrderDTO dto = new RecentOrderDTO();
                    dto.setOrderId("#ORD" + order.getId());

                    // ✅ FETCH REAL CUSTOMER NAME
                    String customerName = userRepository
                            .findById(order.getUserId())
                            .map(User::getFullName)   // ✅ FIXED METHOD NAME
                            .orElse("Unknown User");

                    dto.setCustomer(customerName);
                    dto.setStatus(mapStatus(order.getStatus()));
                    dto.setAmount(order.getTotalPrice());

                    return dto;
                })
                .collect(Collectors.toList());
    }


    // ===================== STATUS MAPPING =====================
    private String mapStatus(String status) {
        if (status == null) return "Pending";

        switch (status.toUpperCase()) {
            case "PAID":
            case "COMPLETED":
                return "Completed";
            case "CANCELLED":
                return "Cancelled";
            default:
                return "Pending";
        }
    }
}
