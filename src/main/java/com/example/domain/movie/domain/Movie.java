package com.example.domain.movie.domain;

import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Entity
@Table(name = "movie")
public class Movie {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movieCode;
    private String movieName;
    private LocalDate movieRelease;
    private String movieGenre;


}
