package com.example.domain.seat.service;

import com.example.domain.schedule.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class SeatFacadeService {

    private final SeatService seatService;
    private final ScheduleService scheduleService;

//    public List<SeatListResponseDto> getSeatListByScheduleId(Long scheduleId){
//
//        Schedule schedule = scheduleService.findById(scheduleId);
//        Theater theater = schedule.getTheater();
//        List<SeatListResponseDto> seatList = seatService.findAllByTheaterId(theater.getId());
//
//        return seatList;
//    }

}
