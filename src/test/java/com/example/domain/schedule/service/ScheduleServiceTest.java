package com.example.domain.schedule.service;

import com.example.domain.movie.domain.Movie;
import com.example.domain.theater.domain.Theater;
import com.example.domain.theater.repository.TheaterRepository;
import com.example.domain.schedule.domain.request.SaveScheduleRequestDto;
import com.example.domain.schedule.service.ScheduleFacadeService;
import com.example.domain.schedule.domain.response.ScheduleListResponseDto;
import com.example.domain.movie.repository.MovieRepository;
import com.example.domain.schedule.service.ScheduleService;
import com.example.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
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

@SpringBootTest
@Rollback
@Transactional
class ScheduleServiceTest {

    @Autowired
    ScheduleService scheduleService;

    @Autowired
    ScheduleFacadeService facadeService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    TheaterRepository theaterRepository;

    @Test
    @DisplayName("영화 스케줄을 등록 할 수 있다.")
    void addSchedule() {
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


        // given
        SaveScheduleRequestDto build = SaveScheduleRequestDto.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .time(LocalDateTime.parse("2023-07-05T12:00")).build();

        Long aLong = facadeService.saveSchedule(build);

        facadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .time(LocalDateTime.parse("2023-07-05T18:00")).build());

        facadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .time(LocalDateTime.parse("2023-07-06T12:00")).build());

        facadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .time(LocalDateTime.parse("2023-07-07T18:00")).build());

        // when
        assertEquals(1,aLong);


    }

    @Test
    @DisplayName("영화id 로 스케줄 리스트를 조회 할 수 있다.")
    void scheduleList() {
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


        // given
        SaveScheduleRequestDto build = SaveScheduleRequestDto.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .time(LocalDateTime.parse("2023-07-05T12:00")).build();

        Long aLong = facadeService.saveSchedule(build);
        long l = aLong.longValue();
        System.out.println("l = " + l);

        facadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .time(LocalDateTime.parse("2023-07-05T18:00")).build());

        facadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .time(LocalDateTime.parse("2023-07-06T12:00")).build());

        facadeService.saveSchedule(SaveScheduleRequestDto.builder()
                .movieId(movieId)
                .theaterId(theaterId)
                .time(LocalDateTime.parse("2023-07-07T18:00")).build());

        // when
        List<ScheduleListResponseDto> scheduleList = scheduleService.findAllByMovieId(movieId);

        for (ScheduleListResponseDto d : scheduleList) {
            System.out.println("d = " + d.getName());
            System.out.println("d.getTheater() = " + d.getTheater());
            System.out.println(d.getTime());

        }

        // then
        assertEquals(4, scheduleList.size());


    }

}