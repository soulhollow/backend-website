package com.example.backend.dto;

public class CreateCheckoutSessionResponse {
    private String sessionId;

    public CreateCheckoutSessionResponse(String sessionId) {
        this.sessionId = sessionId;
    }

    // Getter und Setter
    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}

