package com.example.domain.user.service;

import com.example.domain.movie.domain.Movie;
import com.example.domain.movie.repository.MovieRepository;
import com.example.domain.payment.domain.PaidSeat;
import com.example.domain.payment.domain.Payment;
import com.example.domain.payment.domain.request.FixPaymentRequestDto;
import com.example.domain.payment.domain.request.PaymentRequestDto;
import com.example.domain.payment.domain.request.SeatSelectedDto;
import com.example.domain.payment.domain.response.PaymentResponseDto;
import com.example.domain.payment.service.PaymentFacadeService;
import com.example.domain.payment.service.PaymentService;
import com.example.domain.schedule.domain.request.SaveScheduleRequestDto;
import com.example.domain.schedule.domain.response.ScheduleListResponseDto;
import com.example.domain.schedule.service.ScheduleFacadeService;
import com.example.domain.schedule.service.ScheduleService;
import com.example.domain.seat.domain.Seat;
import com.example.domain.seat.domain.Sold;
import com.example.domain.seat.domain.request.SaveSeatRequestDto;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import com.example.domain.seat.repository.SeatRepository;
import com.example.domain.seat.service.SeatService;
import com.example.domain.theater.domain.Theater;
import com.example.domain.theater.repository.TheaterRepository;
import com.example.domain.user.domain.Rank;
import com.example.domain.user.domain.User;
import com.example.domain.user.domain.response.MyPaymentResponseDto;
import com.example.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.domain.payment.domain.PaymentType.CASH;
import static com.example.domain.user.domain.Role.COMMON;

@SpringBootTest
@Transactional
@Rollback(value = false)
class UserPaymentListTest {


    @Autowired
    SeatService seatService;

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    ScheduleFacadeService scheduleFacadeService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TheaterRepository theaterRepository;

    @Autowired
    SeatRepository seatRepository;

    @Autowired
    PaymentFacadeService paymentFacadeService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    UserService userService;

    @Test
    @DisplayName("유저는 본인이 결제한 건을 모두 조회 할 수 있다.")
    void postPayment() {

        User user = userRepository.save(User.builder()
                .name("user")
                .birthdate(LocalDate.parse("2020-01-01"))
                .mail("test1")
                .role(COMMON)
                .rank(Rank.GOLD)
                .password("1111")
                .build());
        Long userId = user.getId();
        // given
        Movie movie1 = movieRepository.save(Movie.builder()
                .name("타짜")
                .genre("코미디")
                .release(LocalDate.parse("2023-06-29"))
                .build());
        Long movieId = movie1.getId();

        Theater gangNam = theaterRepository.save(Theater.builder()
                .name("강남1관")
                .location("강남역")
                .build());
        Long theaterId = gangNam.getId();

        for (int i = 1; i < 11; i++) {
            seatService.save(
                    SaveSeatRequestDto.builder()
                            .name("A" + i)
                            .theaterId(gangNam)
                            .status(Sold.ABLE)
                            .build());

        }
        // given
        scheduleFacadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .time(LocalDateTime.parse("2023-07-05T12:00")).build());


        List<ScheduleListResponseDto> scheduleList = scheduleService.findAllByMovieId(movieId);

        ScheduleListResponseDto schedule = scheduleList.get(0);

        Long scheduleId = schedule.getId();

        List<SeatListResponseDto> list = scheduleFacadeService.getSeatListByScheduleId(scheduleId);

        SeatSelectedDto dto = new SeatSelectedDto(1L);
        SeatSelectedDto dto1 = new SeatSelectedDto(2L);

        List<SeatSelectedDto> seatList = new ArrayList<>();
        seatList.add(dto);
        seatList.add(dto1);

        PaymentResponseDto price = paymentFacadeService.getPrice(scheduleId,

                PaymentRequestDto.builder()
                        .scheduleId(scheduleId)
                        .count(2)
                        .seatList(seatList)
                        .payment(CASH)
                        .userId(userId)
                        .build());


        paymentFacadeService.payMyMovie(
                scheduleId, FixPaymentRequestDto.builder()
                        .amountToPay(price.getAmountToPay())
                        .count(2)
                        .payment(CASH)
                        .seatList(seatList)
                        .userId(userId)
                        .scheduleId(scheduleId)
                        .build()
        );

        List<MyPaymentResponseDto> ticket = userService.getTicket(userId);
        List<PaidSeat> seatList1 = ticket.get(0).getSeatList();
        for (PaidSeat paidSeat : seatList1) {
            System.out.println("paidSeat.getSeatName() = " + paidSeat.getSeatName());
        }

        Assertions.assertEquals(CASH, ticket.get(0).getPayment());


    }
}
