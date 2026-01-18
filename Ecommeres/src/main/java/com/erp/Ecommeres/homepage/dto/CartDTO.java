package com.erp.Ecommeres.homepage.dto;

import lombok.Data;

@Data
public class CartDTO {

    private Long id;
    private Long productId;
    private String productName;
    private String imageUrl;
    private Integer quantity;
    private Double price;
    private Double totalPrice;
}
