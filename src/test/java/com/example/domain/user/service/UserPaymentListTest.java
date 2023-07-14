package com.example.domain.user.service;

import com.example.domain.movie.repository.MovieRepository;
import com.example.domain.payment.domain.PaidSeat;
import com.example.domain.payment.domain.request.FixPaymentRequestDto;
import com.example.domain.payment.domain.request.PaymentRequestDto;
import com.example.domain.payment.domain.request.SeatSelectedDto;
import com.example.domain.payment.domain.response.PaymentResponseDto;
import com.example.domain.payment.repository.PaidSeatRepository;
import com.example.domain.payment.service.PaymentFacadeService;
import com.example.domain.payment.service.PaymentService;
import com.example.domain.schedule.domain.request.SaveScheduleRequestDto;
import com.example.domain.schedule.domain.response.ScheduleListResponseDto;
import com.example.domain.schedule.service.ScheduleFacadeService;
import com.example.domain.schedule.service.ScheduleService;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import com.example.domain.seat.repository.SeatRepository;
import com.example.domain.seat.service.SeatService;
import com.example.domain.theater.repository.TheaterRepository;
import com.example.domain.user.domain.response.MyPaymentResponseDto;
import com.example.domain.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

import static com.example.domain.payment.domain.PaymentType.CASH;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserPaymentListTest {

    @Autowired
    PaymentFacadeService paymentFacadeService;

    @Autowired
    UserFacadeService userFacadeService;

    @Autowired
    ScheduleFacadeService scheduleService;

    Long scheduleId = 2L;
    Long userId = 3L;

    @BeforeEach
    void before() {

        List<SeatListResponseDto> seatListByScheduleId = scheduleService.getSeatListByScheduleId(scheduleId);

        Long id = seatListByScheduleId.get(0).getId();
        Long id1 = seatListByScheduleId.get(2).getId();


        // given
        SeatSelectedDto dto = new SeatSelectedDto(id);
        SeatSelectedDto dto1 = new SeatSelectedDto(id1);

        List<SeatSelectedDto> seatList = new ArrayList<>();
        seatList.add(dto);
        seatList.add(dto1);

        PaymentResponseDto price = paymentFacadeService.getPrice(scheduleId,
                PaymentRequestDto.builder()
                        .scheduleId(scheduleId)
                        .count(seatList.size())
                        .seatList(seatList)
                        .payment(CASH)
                        .userId(userId)
                        .build());


        paymentFacadeService.payMyMovie(scheduleId,
                FixPaymentRequestDto.builder().
                        amountToPay(price.getAmountToPay()).
                        count(2).
                        payment(CASH).
                        seatList(seatList).
                        userId(userId).
                        scheduleId(scheduleId).
                        build());
    }

    @Test
    @DisplayName("유저는 본인이 결제한 건을 모두 조회 할 수 있다.")
    void getPaymentList() {

        List<MyPaymentResponseDto> ticket = userFacadeService.getTicket(userId);
        System.out.println("\n\n");
        System.out.println("ticket = " + ticket.get(0).getSeatList());
        System.out.println("\n\n");

        for (MyPaymentResponseDto seat : ticket) {
            List<PaidSeat> seatList = seat.getSeatList();
            for (PaidSeat paidSeat : seatList) {
                System.out.println("paidSeat.getSeatName() = " + paidSeat.getSeatName());
            }
        }

    }


}
