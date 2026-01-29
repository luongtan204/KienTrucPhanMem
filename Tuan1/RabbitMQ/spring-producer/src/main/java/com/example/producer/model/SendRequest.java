package com.example.producer.model;

import jakarta.validation.constraints.NotBlank;

public class SendRequest {

    @NotBlank
    private String message;

    private String orderId;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
