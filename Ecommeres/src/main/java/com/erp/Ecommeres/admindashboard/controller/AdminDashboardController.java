package com.erp.Ecommeres.admindashboard.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.*;

import com.erp.Ecommeres.admindashboard.dto.DashboardResponseDTO;
import com.erp.Ecommeres.admindashboard.dto.RecentOrderDTO;
import com.erp.Ecommeres.admindashboard.repo.ProductRepo;
import com.erp.Ecommeres.entity.User;
import com.erp.Ecommeres.homepage.entity.Order;
import com.erp.Ecommeres.homepage.repo.OrderRepo;
import com.erp.Ecommeres.repo.UserRepo;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin("*")
public class AdminDashboardController {

    private final UserRepo userRepository;
    private final ProductRepo productRepository;
    private final OrderRepo orderRepository;

    public AdminDashboardController(UserRepo userRepository,
                                    ProductRepo productRepository,
                                    OrderRepo orderRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    // ===================== DASHBOARD CARDS =====================
    @GetMapping("/dashboard")
    public DashboardResponseDTO getDashboardData() {

        DashboardResponseDTO response = new DashboardResponseDTO();

        response.setTotalCustomers(userRepository.countByRole("USER"));
        response.setTotalProducts(productRepository.count());
        response.setTotalOrders(orderRepository.count());

        // ✅ FIXED REVENUE
        response.setTotalRevenue(orderRepository.getTotalRevenue1());

        return response;
    }

    // ===================== RECENT ORDERS =====================
    @GetMapping("/recent-orders")
    public List<RecentOrderDTO> getRecentOrders() {

        List<Order> orders =
                orderRepository.findTop5ByOrderByCreatedAtDesc();

        return orders.stream()
                .map(order -> {

                    RecentOrderDTO dto = new RecentOrderDTO();

                    dto.setOrderId("#ORD" + order.getId());

                    String customerName = userRepository
                            .findById(order.getUserId())
                            .filter(u -> "USER".equals(u.getRole()))
                            .map(User::getFullName)
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
            case "DELIVERED":
                return "Completed";

            case "SHIPPED":
                return "Shipped";

            case "CANCELLED":
                return "Cancelled";

            case "PLACED":
            default:
                return "Pending";
        }
    }
}
