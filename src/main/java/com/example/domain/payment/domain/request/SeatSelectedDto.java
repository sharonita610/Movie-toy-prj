package com.example.domain.payment.domain.request;

import com.example.domain.payment.domain.PaidSeat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SeatSelectedDto {

    private Long seatId;

}
