package com.example.domain.user.domain.request;

import com.example.domain.user.domain.User;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Getter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Builder
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


    public User toEntity(SignUpRequestDto dto) {
        return User.builder()
                .name(dto.getName())
                .phone(dto.getPhone())
                .birthdate(dto.getBirthdate())
                .mail(dto.getMail())
                .password(dto.getPassword())
                .build();
    }

    public void setUserPw(String password) {
        this.password = password;
    }
}
