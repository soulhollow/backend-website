package com.example.backend.config;

import jakarta.annotation.*;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StripeConfig {

        @Value("${stripe.secret.key}")
        private String secretKey;

        @PostConstruct
        public void init() {
                Stripe.apiKey = secretKey;
        }

}
