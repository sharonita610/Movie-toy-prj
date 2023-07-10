package com.example.domain.movie.domain.request;

import com.example.domain.movie.domain.Movie;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Builder
public class UpdateMovieRequestDto {

    @NotBlank
    private String name;

    @NotBlank
    private LocalDate release;

    @NotBlank
    private String genre;

}
