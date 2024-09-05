package com.example.backend.service;

import com.example.backend.dto.PaymentRequest;
import com.example.backend.model.Order;
import com.example.backend.model.Payment;
import com.example.backend.repository.OrderRepository;
import com.example.backend.repository.PaymentRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public PaymentService() {
        Stripe.apiKey = stripeSecretKey;
    }

    public PaymentIntent createPaymentIntent(PaymentRequest paymentRequest) throws StripeException {
        Optional<Order> order = orderRepository.findById(paymentRequest.getOrderId());
        if (!order.isPresent()) {
            throw new RuntimeException("Bestellung nicht gefunden");
        }

        PaymentIntentCreateParams createParams = PaymentIntentCreateParams.builder()
                .setAmount(paymentRequest.getAmount().multiply(BigDecimal.valueOf(100)).longValue()) // Betrag in Cent
                .setCurrency("usd")
                .setAutomaticPaymentMethods(PaymentIntentCreateParams.AutomaticPaymentMethods.builder().setEnabled(true).build())
                .build();

        return PaymentIntent.create(createParams);
    }

    public Optional<Payment> getPaymentStatus(String paymentId) {
        return paymentRepository.findByStripePaymentId(paymentId);
    }

    public void savePayment(Payment payment) {
        paymentRepository.save(payment);
    }
}
