package com.example.domain.user.domain.response;


import com.example.domain.user.domain.User;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignUpResponseDto {

    private String mail;
    private String name;

    public SignUpResponseDto(User user) {
        this.mail = user.getMail();
        this.name = user.getName();
    }
}
