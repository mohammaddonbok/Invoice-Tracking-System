package com.example.training.DTO;

import com.example.training.models.User;
import com.fasterxml.jackson.annotation.JsonProperty;


public class InvoiceDto {
    @JsonProperty("id")
    Long id;
    String customerName;
     int totalPrice;
     int invoiceNumber;
     User owner;
     String invoiceDate;
    @JsonProperty("isActive")
    Boolean isActive;

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Long getId() {
        return id;
    }
}
