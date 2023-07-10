package com.example.domain.payment.repository;

import com.example.domain.payment.domain.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p.amount FROM Payment p")
    double getAmount();
}
