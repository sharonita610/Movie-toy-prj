package com.example.domain.payment.domain;

import com.example.domain.movie.domain.Schedule;
import com.example.domain.user.domain.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Table(name = "payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pay_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaymentType payment;

    private LocalDateTime time;
    private double amount;
    private String status;
    private LocalDateTime cancelPayment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_id" , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Schedule schedule;


}
