package com.erp.Ecommeres.profile.dto;

import lombok.Data;

@Data
public class AddressResponseDTO {

    private String fullName;
    private String mobileNumber;

    private String addressLine;
    private String city;
    private String state;
    private String pincode;
}
