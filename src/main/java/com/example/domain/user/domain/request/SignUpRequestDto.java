package com.example.domain.user.domain.request;

import com.example.domain.user.domain.Role;
import com.example.domain.user.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SignUpRequestDto {

    @NotBlank
    private String name;

    private LocalDate birthdate;

    private String phone;

    @NotBlank
    private String mail;

    @NotBlank
    private String password;

    @NotBlank
    private Role role;


    public User toEntity(String encodedPassword) {

        return User.builder()
                .name(name)
                .phone(phone)
                .birthdate(birthdate)
                .mail(mail)
                .role(role)
                .password(encodedPassword)
                .build();
    }

}
