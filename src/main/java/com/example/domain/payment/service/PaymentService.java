package com.example.domain.payment.service;

import com.example.domain.payment.domain.Payment;
import com.example.domain.payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PaymentService {

    private final PaymentRepository paymentRepository;

    public double getAmount(){
        Payment payment = Payment.builder().build();
         return payment.getAmount();
    }

}
