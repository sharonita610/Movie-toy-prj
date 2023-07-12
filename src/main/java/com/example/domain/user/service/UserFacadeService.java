package com.example.domain.user.service;

import com.example.domain.payment.domain.PaidSeat;
import com.example.domain.payment.domain.Payment;
import com.example.domain.payment.domain.request.SeatSelectedDto;
import com.example.domain.payment.domain.response.PaidSeatResponseDto;
import com.example.domain.payment.service.PaidSeatService;
import com.example.domain.payment.service.PaymentService;
import com.example.domain.seat.domain.Seat;
import com.example.domain.seat.domain.Sold;
import com.example.domain.seat.service.SeatService;
import com.example.domain.user.domain.response.MyPaymentResponseDto;
import com.example.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.global.exception.ErrorCode.*;

@Service
@Transactional
@RequiredArgsConstructor
public class UserFacadeService {

    private final UserService userService;
    private final PaymentService paymentService;
    private final SeatService seatService;
    private final PaidSeatService paidSeatService;
    public boolean cancel(Long id, Long paymentId) {
        List<MyPaymentResponseDto> ticket = userService.getTicket(id);
        findMyPayment(paymentId, ticket);
//        cancelSeatStatus(paymentId);
        cancelPayment(paymentId);
        return true;
    }
//
//    private void cancelSeatStatus(Long paymentId) {
//        List<PaidSeatResponseDto> paidSeats = paidSeatService.findByPayId(paymentId);
//        int size = paidSeats.size();
//        System.out.println("size = " + size);
//
//        for (PaidSeatResponseDto paidSeat : paidSeats) {
//            String seatName = paidSeat.getPaidSeatName();
//            Seat seat = seatService.findByName(seatName);
//            Sold status = seat.getStatus();
//            if (status == Sold.SOLD) {
//                status = Sold.ABLE;
//                seat.updateStatus(status);
//
//            }
//        }
//
//    }

    private void cancelPayment(Long paymentId) {
        Payment payment = paymentService.findById(paymentId);
        LocalDateTime startTime = payment.getSchedule().getTime();

        if (startTime.isBefore(LocalDateTime.now())) throw new CustomException(TIME_OVER.getMessage(), TIME_OVER);
        else payment.cancelPayment("CANCEL", LocalDateTime.now());
    }

    private static void findMyPayment(Long paymentId, List<MyPaymentResponseDto> ticket) {
        boolean payIdExist = ticket.stream()
                .map(MyPaymentResponseDto::getPaymentId)
                .anyMatch(rawId -> rawId.equals(paymentId));

        if (!payIdExist) throw new CustomException(PAYMENT_NOT_FOUND.getMessage(), PAYMENT_NOT_FOUND);
    }
}
