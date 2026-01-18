package com.erp.Ecommeres.homepage.dto;


import lombok.Data;
import java.util.List;

@Data
public class CartResponseDTO {

    private List<CartDTO> items;
    private Double subTotal;
    private Double gst;
    private Double grandTotal;
}
