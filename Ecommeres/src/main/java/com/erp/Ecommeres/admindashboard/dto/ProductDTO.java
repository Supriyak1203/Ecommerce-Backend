package com.erp.Ecommeres.admindashboard.dto;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String productName;
    private String category;
    private Double price;
    private String imageUrl;
    private String description;
    private String usage;
    private String details;
    private Integer quantity;
}
