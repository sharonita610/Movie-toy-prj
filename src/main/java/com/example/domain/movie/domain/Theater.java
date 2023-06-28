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
    @Column(name = "theater_id")
    private Long id;

    @Column(name = "theater_name")
    private String name;

    @Column(name = "theater_loc")
    private String location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seat_id" , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Seat seat;

    private int seatCount;

}
