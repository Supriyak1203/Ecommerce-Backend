package com.erp.Ecommeres.homepage.dto;


import lombok.Data;

@Data
public class WishlistResponseDTO {
    private String status; // ADDED or REMOVED
    private WishlistDTO item;
}
