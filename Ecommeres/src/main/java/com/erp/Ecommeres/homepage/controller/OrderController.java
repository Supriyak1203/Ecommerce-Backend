package com.erp.Ecommeres.homepage.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.erp.Ecommeres.admindashboard.entity.Product;
import com.erp.Ecommeres.admindashboard.repo.ProductRepo;
import com.erp.Ecommeres.homepage.dto.CreateOrderRequestDTO;
import com.erp.Ecommeres.homepage.dto.ShippingUpdateRequest;
import com.erp.Ecommeres.homepage.entity.Order;
import com.erp.Ecommeres.homepage.repo.OrderRepo;
import com.erp.Ecommeres.profile.entity.Address;
import com.erp.Ecommeres.profile.repo.AddressRepo;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {

    private final OrderRepo orderRepo;
    private final AddressRepo addressRepo;
    private final ProductRepo productRepo;

    public OrderController(OrderRepo orderRepo,
                           AddressRepo addressRepo,
                           ProductRepo productRepo) {
        this.orderRepo = orderRepo;
        this.addressRepo = addressRepo;
        this.productRepo = productRepo;
    }

    // ✅ CASH ON DELIVERY ORDER
    @PostMapping("/cod")
    public String placeCodOrder(@RequestBody CreateOrderRequestDTO dto) {

        Address address = addressRepo
                .findTopByUserIdOrderByCreatedAtDesc(dto.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() ->
                        new RuntimeException("Product not found"));

        double totalPrice = product.getPrice() * dto.getQuantity();

        String fullAddress =
                address.getAddressLine() + ", " +
                address.getCity() + ", " +
                address.getState() + " - " +
                address.getPincode();

        Order order = new Order();
        order.setUserId(dto.getUserId());
        order.setProductId(product.getId());
        order.setProductName(product.getProductName());
        order.setQuantity(dto.getQuantity());

        order.setTotalPrice(totalPrice);
        order.setPaymentOption("COD");


        order.setAddress(fullAddress);
        order.setStatus("PLACED");

        orderRepo.save(order);

        return "COD_ORDER_PLACED";
    }

    // ✅ ADMIN FETCH ALL ORDERS
    @GetMapping
    public List<Order> getAllOrders() {
        return orderRepo.findAll();
    }

    @GetMapping("/search")
    public List<Order> searchOrders(@RequestParam String keyword) {
        return orderRepo.searchOrders(keyword);
    }
    
    @PutMapping("/{orderId}")
    public Order updateShippingDetails(
            @PathVariable Long orderId,
            @RequestBody ShippingUpdateRequest request) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setShippingPartner(request.getShippingPartner());
        order.setPartnerContact(request.getPartnerContact());
        order.setExpectedDelivery(request.getExpectedDelivery());

        // optional: update status automatically
        order.setStatus("SHIPPED");

        return orderRepo.save(order);
    }

}
