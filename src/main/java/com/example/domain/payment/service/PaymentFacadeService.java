package com.example.domain.payment.service;

import com.example.domain.movie.domain.Movie;
import com.example.domain.payment.domain.PaymentRequestDto;
import com.example.domain.payment.domain.PaymentResponseDto;
import com.example.domain.payment.domain.SeatSelectedDto;
import com.example.domain.schedule.domain.Schedule;
import com.example.domain.schedule.service.ScheduleService;
import com.example.domain.theater.domain.Theater;
import com.example.domain.user.domain.Rank;
import com.example.domain.user.domain.User;
import com.example.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class PaymentFacadeService {

    private final PaymentService paymentService;
    private final ScheduleService scheduleService;
    private final UserService userService;


    public PaymentResponseDto getPrice(Long scheduleId, PaymentRequestDto dto) {

        Schedule schedule = scheduleService.findById(scheduleId);
        User foundUser = userService.findById(dto.getUserId());
        return new PaymentResponseDto(schedule, foundUser.getRank(), dto.getCount(), dto.getSeatList(), getDiscountedPrice(dto.getCount(), foundUser.getRank(), paymentService.getAmount()));
    }


    private static double getDiscountedPrice(int count, Rank rank, double amount) {
        double amountWithDc = rank.getDiscount(amount);
        return amountWithDc * count;
    }
}
