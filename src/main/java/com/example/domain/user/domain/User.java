package com.example.domain.user.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Builder
@Table(name = "user")
public class User {

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long userCode;

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String userName;
    private LocalDate userBirthdate;
    private String userPhone;

//    @Column(unique = true, nullable = false)
//    private String userId;

    @Column(nullable = false)
    private String userPw;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Rank userRank = Rank.STANDARD;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private Role role = Role.COMMON;


}
