package com.erp.Ecommeres.homepage.controller;



import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erp.Ecommeres.homepage.dto.CreateOrderRequestDTO;
import com.erp.Ecommeres.homepage.entity.Order;
import com.erp.Ecommeres.homepage.repo.OrderRepo;
import com.erp.Ecommeres.profile.entity.Address;
import com.erp.Ecommeres.profile.repo.AddressRepo;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepo orderRepo;
    private final AddressRepo addressRepo;

    public OrderController(OrderRepo orderRepo, AddressRepo addressRepo) {
        this.orderRepo = orderRepo;
        this.addressRepo = addressRepo;
    }

    // ✅ CASH ON DELIVERY ORDER
    @PostMapping("/cod")
    public String placeCodOrder(@RequestBody CreateOrderRequestDTO dto) {

    	Address address = addressRepo
    	        .findTopByUserIdOrderByCreatedAtDesc(dto.getUserId())
    	        .orElseThrow(() ->
    	            new RuntimeException("Address not found for userId: " + dto.getUserId())
    	        );

        String fullAddress =
                address.getAddressLine() + ", " +
                address.getCity() + ", " +
                address.getState() + " - " +
                address.getPincode();

        Order order = new Order();
        order.setUserId(dto.getUserId());
        order.setTotalPrice(dto.getTotalPrice());
        order.setPaymentOption("COD");
        order.setAddress(fullAddress);
        order.setStatus("PLACED");

        orderRepo.save(order);

        return "COD_ORDER_PLACED";
    }
}
