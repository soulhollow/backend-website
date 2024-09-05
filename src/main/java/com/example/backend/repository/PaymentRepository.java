package com.example.backend.repository;

import com.example.backend.model.Order;
import com.example.backend.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByStripePaymentId(String stripePaymentId);
    Optional<Payment> findByOrder(Order order);
}
