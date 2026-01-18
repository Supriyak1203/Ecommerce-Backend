package com.erp.Ecommeres.homepage.controller;


import com.erp.Ecommeres.homepage.service.CheckoutService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkout")
@CrossOrigin("*")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @GetMapping("/summary")
    public Object checkoutSummary(@RequestHeader("userId") Long userId) {
        return checkoutService.getCheckoutSummary(userId);
    }
}
