package com.erp.Ecommeres.homepage.dto;

public class CreateOrderResponseDTO {

    private String orderId;
    private Integer amount;
    private String currency;
    private String key;

    public CreateOrderResponseDTO(String orderId, Integer amount, String currency, String key) {
        this.orderId = orderId;
        this.amount = amount;
        this.currency = currency;
        this.key = key;
    }

    public String getOrderId() { return orderId; }
    public Integer getAmount() { return amount; }
    public String getCurrency() { return currency; }
    public String getKey() { return key; }
}
