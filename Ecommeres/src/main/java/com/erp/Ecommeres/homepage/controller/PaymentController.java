package com.erp.Ecommeres.homepage.controller;

import java.util.Base64;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import com.erp.Ecommeres.admindashboard.entity.Product;
import com.erp.Ecommeres.admindashboard.repo.ProductRepo;
import com.erp.Ecommeres.homepage.dto.CreateOrderRequestDTO;
import com.erp.Ecommeres.homepage.dto.CreateOrderResponseDTO;
import com.erp.Ecommeres.homepage.dto.VerifyPaymentDTO;
import com.erp.Ecommeres.homepage.entity.Order;
import com.erp.Ecommeres.homepage.repo.OrderRepo;
import com.erp.Ecommeres.profile.entity.Address;
import com.erp.Ecommeres.profile.repo.AddressRepo;
import com.razorpay.RazorpayClient;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("*")
public class PaymentController {

    @Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;

    private final OrderRepo orderRepo;
    private final AddressRepo addressRepo;
    private final ProductRepo productRepo;


    public PaymentController(OrderRepo orderRepo,
            AddressRepo addressRepo,
            ProductRepo productRepo) {
this.orderRepo = orderRepo;
this.addressRepo = addressRepo;
this.productRepo = productRepo;
}

    // ==============================
    // ✅ CREATE RAZORPAY ORDER
    // ==============================
    @PostMapping("/create-order")
    public CreateOrderResponseDTO createOrder(
            @RequestBody CreateOrderRequestDTO dto) throws Exception {

        if (dto.getUserId() == null ||
            dto.getProductId() == null ||
            dto.getQuantity() == null ||
            dto.getTotalPrice() == null ||
            dto.getTotalPrice() <= 0) {

            throw new RuntimeException("Invalid payment request");
        }

        Address address = addressRepo
                .findTopByUserIdOrderByCreatedAtDesc(dto.getUserId())
                .orElseThrow(() ->
                        new RuntimeException("Address not found"));

        Product product = productRepo.findById(dto.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        String fullAddress =
                address.getAddressLine() + ", " +
                address.getCity() + ", " +
                address.getState() + " - " +
                address.getPincode();

        RazorpayClient client = new RazorpayClient(keyId, keySecret);

        JSONObject options = new JSONObject();
        options.put("amount", dto.getTotalPrice().intValue() * 100);
        options.put("currency", "INR");
        options.put("receipt", "rcpt_" + System.currentTimeMillis());

        com.razorpay.Order rpOrder = client.orders.create(options);

        Order order = new Order();

        order.setUserId(dto.getUserId());

        // ✅ PRODUCT DETAILS SAVED
        order.setProductId(product.getId());
        order.setProductName(product.getProductName());
        order.setQuantity(dto.getQuantity());

        order.setTotalPrice(dto.getTotalPrice());
        order.setPaymentOption("ONLINE");

        // ✅ ADDRESS SAVED
        order.setAddress(fullAddress);

        order.setStatus("CREATED");
        order.setRazorpayOrderId(rpOrder.get("id"));

        orderRepo.save(order);

        return new CreateOrderResponseDTO(
                rpOrder.get("id"),
                rpOrder.get("amount"),
                "INR",
                keyId
        );
    }

    // ==============================
    // ✅ VERIFY PAYMENT
    // ==============================
    @PostMapping("/verify")
    public String verifyPayment(
            @RequestBody VerifyPaymentDTO dto) throws Exception {

        String payload =
                dto.getRazorpay_order_id() + "|" +
                dto.getRazorpay_payment_id();

        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(
                keySecret.getBytes(),
                "HmacSHA256"));

        byte[] hash = mac.doFinal(payload.getBytes());

        StringBuilder hexString = new StringBuilder();
        for (byte b : hash) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }

        String generatedSignature = hexString.toString();

        Order order = orderRepo
                .findByRazorpayOrderId(
                        dto.getRazorpay_order_id())
                .orElseThrow(() ->
                        new RuntimeException("Order not found"));

        if (generatedSignature
                .equals(dto.getRazorpay_signature())) {

            order.setStatus("PAID");
            order.setRazorpayPaymentId(
                    dto.getRazorpay_payment_id());

            orderRepo.save(order);
            return "PAYMENT_SUCCESS";

        } else {

            order.setStatus("FAILED");
            orderRepo.save(order);
            return "PAYMENT_FAILED";
        }
    }
}
