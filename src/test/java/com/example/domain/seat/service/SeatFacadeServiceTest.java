package com.example.domain.seat.service;

import com.example.domain.movie.domain.Movie;
import com.example.domain.movie.repository.MovieRepository;
import com.example.domain.schedule.domain.request.SaveScheduleRequestDto;
import com.example.domain.schedule.domain.response.ScheduleListResponseDto;
import com.example.domain.schedule.service.ScheduleFacadeService;
import com.example.domain.schedule.service.ScheduleService;
import com.example.domain.seat.domain.*;
import com.example.domain.seat.domain.request.SaveSeatRequestDto;
import com.example.domain.seat.domain.response.SeatListResponseDto;
import com.example.domain.seat.repository.SeatRepository;
import com.example.domain.theater.domain.Theater;
import com.example.domain.theater.repository.TheaterRepository;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Rollback
@Transactional
@SpringBootTest
class SeatFacadeServiceTest {

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

    @Test
    @DisplayName("스케줄 id로 사용 가능한 좌석 리스트와 갯수를 확인 할 수 있어야 한다.")
    void getSeatListByScheduleId() {

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
                            .build());

        }
        // given
        scheduleFacadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .time(LocalDateTime.parse("2023-07-05T12:00")).build());

        scheduleFacadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .time(LocalDateTime.parse("2023-07-05T18:00")).build());

        scheduleFacadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .time(LocalDateTime.parse("2023-07-06T12:00")).build());

        scheduleFacadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .time(LocalDateTime.parse("2023-07-07T18:00")).build());

        List<ScheduleListResponseDto> scheduleList = scheduleService.findAllByMovieId(movieId);

        ScheduleListResponseDto schedule = scheduleList.get(1);

        ScheduleListResponseDto dto = scheduleList.get(0);

        Long scheduleId = schedule.getId();

        List<SeatListResponseDto> list = scheduleFacadeService.getSeatListByScheduleId(scheduleId);

        assertEquals("A1", list.get(0).getName());

// ===============================================================
        Movie movie2 = movieRepository.save(Movie.builder()
                .name("미비포유")
                .genre("로맨스")
                .release(LocalDate.parse("2023-06-29"))
                .build());
        Long movie2Id = movie2.getId();

        Theater hongDae = theaterRepository.save(Theater.builder()
                .name("홍대2관")
                .location("홍대역")
                .build());
        Long hongDaeId = hongDae.getId();

        for (int i = 1; i < 11; i++) {
            seatService.save(
                    SaveSeatRequestDto.builder()
                            .name("A" + i)
                            .theaterId(hongDae)
                            .build());

        }
        // given
        scheduleFacadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movie2Id)
                .theaterId(hongDaeId)
                .time(LocalDateTime.parse("2023-07-05T12:00")).build());

        scheduleFacadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movie2Id)
                .theaterId(hongDaeId)
                .time(LocalDateTime.parse("2023-07-05T18:00")).build());

        scheduleFacadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movie2Id)
                .theaterId(hongDaeId)
                .time(LocalDateTime.parse("2023-07-06T12:00")).build());

        scheduleFacadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movie2Id)
                .theaterId(hongDaeId)
                .time(LocalDateTime.parse("2023-07-07T18:00")).build());

        List<ScheduleListResponseDto> scheduleList1 = scheduleService.findAllByMovieId(movie2Id);

        ScheduleListResponseDto schedule1 = scheduleList1.get(1);

        Long schedule1Id = schedule1.getId();

        List<SeatListResponseDto> list1 = scheduleFacadeService.getSeatListByScheduleId(schedule1Id);

        assertEquals(10, list1.size());
        assertEquals("A1", list1.get(0).getName());

    }


}