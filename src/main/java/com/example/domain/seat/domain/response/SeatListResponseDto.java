package com.example.domain.seat.domain.response;

import com.example.domain.seat.domain.Seat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SeatListResponseDto {

    private String name;
    private int seatCount;

    public SeatListResponseDto(Seat seat) {
        this.name = seat.getName();
        this.seatCount = seat.getName().length();
    }


}
