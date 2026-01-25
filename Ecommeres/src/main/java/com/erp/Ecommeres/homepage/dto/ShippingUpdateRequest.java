package com.erp.Ecommeres.homepage.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ShippingUpdateRequest {

    private String shippingPartner;
    private String partnerContact;

    // 👇 accept dd-mm-yyyy from frontend
    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate expectedDelivery;

    public String getShippingPartner() {
        return shippingPartner;
    }

    public void setShippingPartner(String shippingPartner) {
        this.shippingPartner = shippingPartner;
    }

    public String getPartnerContact() {
        return partnerContact;
    }

    public void setPartnerContact(String partnerContact) {
        this.partnerContact = partnerContact;
    }

    public LocalDate getExpectedDelivery() {
        return expectedDelivery;
    }

    public void setExpectedDelivery(LocalDate expectedDelivery) {
        this.expectedDelivery = expectedDelivery;
    }
}
