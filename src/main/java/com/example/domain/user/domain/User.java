package com.example.domain.user.domain;

import com.example.domain.payment.domain.Payment;
import com.example.domain.user.domain.request.UpdateUserRequestDto;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
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

    @Column(name = "user_pwd", nullable = false)
    private String password;

    @Builder.Default
    @Column(name = "user_rank")
    @Enumerated(EnumType.STRING)
    private Rank rank = Rank.STANDARD;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;

    @OneToMany(mappedBy = "user")
    private List<Payment> paymentList;


    public void updateUser(UpdateUserRequestDto dto) {
        this.name = dto.getName();
        this.birthdate = dto.getBirthdate();
        this.phone = dto.getPhone();
        this.mail = dto.getMail();
        this.password = dto.getPassword();
    }

    public boolean upgradeRank(Rank newRank) {
        this.rank = newRank;
        return true;
    }

}
