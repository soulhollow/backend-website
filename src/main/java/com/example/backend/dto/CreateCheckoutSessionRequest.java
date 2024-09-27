package com.example.backend.dto;


public class CreateCheckoutSessionRequest {
    private String productId;

    // Getter und Setter
    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }
}
