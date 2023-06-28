package com.example.domain.user.domain.response;

import com.example.domain.user.domain.Rank;
import com.example.domain.user.domain.User;
import lombok.*;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {


    private String name;
    private Rank rank;
    private String phone;

    public LoginResponseDto(User user) {
        this.name = name;
        this.rank = rank;
        this.phone = phone;
    }
}
