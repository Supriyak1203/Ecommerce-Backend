package com.erp.Ecommeres.homepage.dto;

public class CreateOrderRequestDTO {

    private Long userId;
    private Double totalPrice;
    private String paymentOption;

    public Long getUserId() {
        return userId;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public String getPaymentOption() {
        return paymentOption;
    }
}
