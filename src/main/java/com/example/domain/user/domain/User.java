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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @NotBlank
    @Column(name = "user_name")
    private String name;
    @Column(name = "user_birthdate")
    private LocalDate birthdate;
    @Column(name = "user_phone")
    private String phone;

    @Column(name = "user_mail", unique = true, nullable = false)
    private String mail;

    @Column(name = "user_pwd",nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "user_rank")
    private Rank rank = Rank.STANDARD;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(name = "role")
    private Role role = Role.COMMON;


}
