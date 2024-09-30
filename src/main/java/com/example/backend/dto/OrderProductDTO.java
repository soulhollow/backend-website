package com.example.backend.dto;

import java.math.BigDecimal;
public class OrderProductDTO {
    private Long orderId;
    private String productName;
    private Integer quantity;
    private BigDecimal price;
    private String orderStatus; // Neues Feld für den OrderStatus
    // Standard-Konstruktor
    public OrderProductDTO() {}
    // Benutzerdefinierter Konstruktor
    public OrderProductDTO(Long orderId, String productName, Integer quantity, BigDecimal price, String orderStatus) {
        this.orderId = orderId;
        this.productName = productName;
        this.quantity = quantity;
        this.price = price;
        this.orderStatus = orderStatus;
    }
    // Getter und Setter
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public Integer getQuantity() {
        return quantity;
    }
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}