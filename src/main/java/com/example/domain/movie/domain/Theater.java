package com.example.domain.movie.domain;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
@Table(name = "theater")
public class Theater {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long theaterCode;

    private String theaterName;
    private String theaterLoc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_code" , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Seat seat;

    private int seatCount;

}
