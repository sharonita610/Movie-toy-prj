package com.example.domain.seat.domain;

import com.example.domain.theater.domain.Theater;
import lombok.*;

import javax.persistence.*;

import static com.example.domain.seat.domain.Sold.N;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "seat")
public class Seat {

    @Id
    @Column(name = "seat_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "seat_name")
    private String name;

    @Builder.Default
    private Sold status = N;

    @ManyToOne
    @JoinColumn(name= "theater_id")
    private Theater theater;

}
