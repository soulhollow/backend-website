package com.example.backend.service;

import com.example.backend.model.Order;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class PaymentService {

    public PaymentIntent createPaymentIntent(Order order) throws StripeException {
        log.info("Erstelle PaymentIntent für Bestellung: {}", order.getId());
        // Zahlungslogik mit Stripe
    }
}