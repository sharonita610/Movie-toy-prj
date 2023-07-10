package com.example.domain.payment.domain;

import com.example.domain.movie.domain.Movie;
import com.example.domain.schedule.domain.Schedule;
import com.example.domain.theater.domain.Theater;
import com.example.domain.user.domain.Rank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.tomcat.jni.Local;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentResponseDto {

    private int count;

    private Rank rank;
    private double amountToPay;

    private String title;
    private String genre;
    private String theater;
    private String location;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    private List<SeatSelectedDto> seatList;


    public PaymentResponseDto(Schedule schedule, Rank rank, int count, List<SeatSelectedDto> seatList, double discountedPrice) {
        this.count = count;
        this.amountToPay = discountedPrice;
        this.rank = rank;
        this.title = schedule.getMovie().getName();
        this.genre = schedule.getMovie().getGenre();
        this.theater = schedule.getTheater().getName();
        this.location = schedule.getTheater().getLocation();
        this.startTime = schedule.getTime();
        this.endTime = startTime.plusHours(2);
        this.seatList = seatList;

    }

}
