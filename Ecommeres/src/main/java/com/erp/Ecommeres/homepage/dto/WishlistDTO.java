package com.erp.Ecommeres.homepage.dto;


import lombok.Data;

@Data
public class WishlistDTO {

    private Long id;
    private Long productId;
    private String productName;
    private String imageUrl;
}
