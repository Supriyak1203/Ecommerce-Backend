package com.erp.Ecommeres.homepage.service;

import com.erp.Ecommeres.homepage.entity.Cart;
import com.erp.Ecommeres.homepage.repo.CartRepo;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CheckoutService {

    private final CartRepo cartRepo;

    public CheckoutService(CartRepo cartRepo) {
        this.cartRepo = cartRepo;
    }

    public Map<String, Object> getCheckoutSummary(Long userId) {

        List<Cart> cartItems = cartRepo.findByUserId(userId);

        int subtotal = 0;

        for (Cart item : cartItems) {
            subtotal += item.getTotalPrice();
        }

        int gst = (int) (subtotal * 0.05);
        int deliveryCharges = subtotal > 1000 ? 0 : 50;
        int finalAmount = subtotal + gst + deliveryCharges;

        Map<String, Object> response = new HashMap<>();
        response.put("items", cartItems);

        Map<String, Object> priceDetails = new HashMap<>();
        priceDetails.put("subtotal", subtotal);
        priceDetails.put("gst", gst);
        priceDetails.put("deliveryCharges", deliveryCharges);
        priceDetails.put("finalAmount", finalAmount);

        response.put("priceDetails", priceDetails);

        return response;
    }
}
