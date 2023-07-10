package com.example.domain.payment.controller;

import com.example.domain.payment.domain.PaymentRequestDto;
import com.example.domain.payment.domain.PaymentResponseDto;
import com.example.domain.payment.service.PaymentFacadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {

    private final PaymentFacadeService facadeService;

    @GetMapping("/{scheduleId}")
    public ResponseEntity<PaymentResponseDto> getPaymentAmount(@PathVariable Long scheduleId, PaymentRequestDto dto){
        return ResponseEntity.ok().body(facadeService.getPrice(scheduleId, dto));
    }
}
