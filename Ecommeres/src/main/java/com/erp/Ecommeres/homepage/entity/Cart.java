package com.erp.Ecommeres.homepage.entity;


import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "carts")
@Data
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Long productId;

    private Integer quantity;

    private Double price;

    private Double totalPrice;

    private LocalDateTime createdAt = LocalDateTime.now();
}
