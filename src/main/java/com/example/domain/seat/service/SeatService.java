package com.example.domain.seat.service;

import com.example.domain.seat.domain.request.SaveSeatRequestDto;
import com.example.domain.seat.domain.Seat;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import com.example.domain.seat.repository.SeatRepository;
import com.example.global.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

import static com.example.global.exception.ErrorCode.DUPLICATE_SEAT;
import static java.util.stream.Collectors.toList;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatService {

    private final SeatRepository seatRepository;

    public List<SeatListResponseDto> findAll() {
        return seatRepository.findAll().stream().map(SeatListResponseDto::new).collect(toList());
    }

    public boolean save(SaveSeatRequestDto dto) {
        seatRepository.save(dto.toEntity());
        return true;
    }

    public List<SeatListResponseDto> findAllByTheaterId(Long theaterId){
        return seatRepository.findAllByTheaterId(theaterId);
    }

}
