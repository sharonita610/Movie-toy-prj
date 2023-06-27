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
    private Long payCode;

    @Enumerated(EnumType.STRING)
    private PaymentType payType;

    private LocalDateTime payTime;
    private double payAmount;
    private String payStatus;
    private LocalDateTime payCanceltime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_code", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "schedule_code" , foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Schedule schedule;


}
