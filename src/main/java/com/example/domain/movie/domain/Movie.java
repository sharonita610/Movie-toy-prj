package com.example.domain.movie.domain;

import lombok.*;


import javax.persistence.*;
import java.time.LocalDate;

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
    @Column(name = "movie_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "movie_name")
    private String name;

    @Column(name = "movie_release")
    private LocalDate release;

    @Column(name = "movie_genre")
    private String genre;


}
